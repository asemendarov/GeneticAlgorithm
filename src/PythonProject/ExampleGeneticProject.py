import numpy as np
import matplotlib.pyplot as plt

epoch = 50
rarity_mutation = 100
population_size = 200
chromosome_size = 64
chromosome_min_value = 0
chromosome_max_value = 79

population = np.random.randint(2, size=(population_size, chromosome_size))  # БКГ

chromosome_size = (chromosome_size >> 1) * 2

f1 = lambda x, y: 0.2 * (x - 70) ** 2 + 0.8 * (y - 20) ** 2
f2 = lambda x, y: 0.2 * (x - 10) ** 2 + 0.8 * (y - 70) ** 2


def DecodeGrayToInteger(chromosomeCodeGray):
    z1 = chromosomeCodeGray[0:len(chromosomeCodeGray) >> 1]
    z2 = chromosomeCodeGray[len(chromosomeCodeGray) >> 1:]

    resultInt = []

    for z in [z1, z2]:
        s1 = z[0] * (2 << len(z) - 2)
        s2 = 0
        for i in range(1, len(z) - 1):
            summa = 0

            for j in range(0, i):
                summa = summa + z[j]

            summa = summa % 2
            summa = summa * (1 - 2 * z[i]) + z[i]
            summa = summa * (2 << len(z) - i - 2)
            s2 = s2 + summa

        summa = 0
        for i in range(0, len(z) - 1):
            summa = summa + z[i]

        summa = summa % 2

        s3 = summa * (1 - 2 * z[len(z) - 1])
        s4 = z[len(z) - 1]

        resultInt.append(s1 + s2 + s3 + s4)

    return resultInt


def DecodeGrayToFloat(chromosomeCodeGray, chromosome_min_value, chromosome_max_value, chromosome_size):
    [L1, L2] = DecodeGrayToInteger(chromosomeCodeGray)
    fun = lambda L: chromosome_min_value + (L * (chromosome_max_value - chromosome_min_value)) / (
            (2 << (chromosome_size >> 1) - 1) - 1)
    return [fun(L1), fun(L2)]


# q - параметр, влияющий на скорость сходимости генетического алгоритма к оптимуму
def fitFunction(f_results, q=1):
    b = [0] * len(f_results)
    fit_index = []
    for i in range(0, len(f_results)):
        for j in range(0, len(f_results)):
            if (i != j) and ((f_results[i][0] > f_results[j][0]) and (f_results[i][1] > f_results[j][1])):
                b[i] = b[i] + 1
        fit_index.append(1 / (1 + (b[i] / (len(f_results) - 1))) ** 1)
    return fit_index


def crossing_over_multiple_point(parent1, parent2, k=1):
    chromosome = parent1
    even_block = np.random.randint(2)
    n = len(parent1) - 1
    rand_indexes = np.sort(np.unique(np.random.randint(n, size=(1, k))[0]))
    prev_index = 1

    for i in range(0, len(rand_indexes)):
        if i == len(rand_indexes):
            end_index = len(chromosome)
        else:
            end_index = rand_indexes[i]

        if i % 2 == (1 if even_block == 0 else 0):
            chromosome[prev_index: end_index] = parent2[prev_index: end_index]

        prev_index = rand_indexes[i]

    return chromosome


def mutation_multiple_point(chromosome, k=1):
    n = len(chromosome) - 1
    rand_indexes = np.random.randint(n, size=(1, k))[0]
    for i in range(0, k):
        x = chromosome[rand_indexes[i]]
        chromosome[rand_indexes[i]] = chromosome[rand_indexes[i] + 1]
        chromosome[rand_indexes[i] + 1] = x

    return chromosome


fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(12, 6))
ax1, ax2 = ax.flatten()
ax1.set_title('Множество Парето до применения алгоритма')
ax1.set_xlabel('$f_1$')
ax1.set_ylabel('$f_2$')
ax2.set_title('Множество Парето после применения алгоритма')
ax2.set_xlabel('$f_1$')
ax2.set_ylabel('$f_2$')


def plot_pareto(f_results, pareto, ax, epoch_number):
    for i in range(0, len(f_results)):
        if pareto[i] == 1:
            ax.scatter(f_results[i][0], f_results[i][1], c='green', s=30)
        elif pareto[i] > 0.8:
            ax.scatter(f_results[i][0], f_results[i][1], c='blue', s=30)
        elif pareto[i] > 0.6:
            ax.scatter(f_results[i][0], f_results[i][1], c='yellow', s=30)
        else:
            ax.scatter(f_results[i][0], f_results[i][1], c='read', s=30)


if __name__ == '__main__':

    ##
    #   Step 0
    ##
    epoch_index = 1

    while True:
        # Step 1 — Бинарный код грея в целое число и после в вещественное
        f_results = []
        for i in range(0, population_size):
            [R1, R2] = DecodeGrayToFloat(population[i], chromosome_min_value, chromosome_max_value, chromosome_size)
            f_results.append([f1(R1, R2), f2(R1, R2)])

        ##
        #  Step 2. Финтес функция
        ##
        fit_index = fitFunction(f_results)
        S = sum(fit_index)
        k = population_size
        rn = np.random.uniform(size=(1, k))[0]

        qqq = abs(min(rn))
        for i in range(0, len(rn)):
            rn[i] = rn[i] + qqq

        qqq = (S / max(rn))
        for i in range(0, len(rn)):
            rn[i] = rn[i] * qqq

        parents = []
        for i in range(0, len(rn)):
            k = 0
            rn0 = 0
            for j in range(0, population_size):
                k = j
                if (rn[i] >= rn0) and (rn[i] < (rn0 + fit_index[j])):
                    break
                rn0 = rn0 + fit_index[j]
            parents.append(population[k])

        # Печать первой эпохи
        if epoch_index == 1:
            plot_pareto(f_results, fit_index, ax1, epoch_index)

        # Печать последней
        if epoch_index == epoch:
            plot_pareto(f_results, fit_index, ax2, epoch_index)

        ##
        #  Step 3. Кросовер и мутация
        ##
        childs = []

        # Создаем список номеров возможных родителей (список одиночек)
        parent1_list_index = [i for i in range(0, population_size)]
        parent2_list_index = [i for i in range(0, population_size)]

        # Приходимся по всем парам
        for i in range(0, population_size):

            # Случайно выбираем номера партнеров для скрещивания из списка одиночек
            index1 = np.random.choice(parent1_list_index)
            index2 = np.random.choice(parent2_list_index)

            # Удаляем номера выбранных партнеров из списка одиночек
            parent1_list_index.remove(index1)
            parent2_list_index.remove(index2)

            # Находим будущих родителей по номеру и запоминаем их
            parent1 = np.copy(parents[index1])
            parent2 = np.copy(parents[index2])

            # Скрещиваем их геном
            chromosome = crossing_over_multiple_point(parent1, parent2)

            # Подвергаем мутации
            if np.random.randint(rarity_mutation) == 1:  # Вероятность мутации 1%, т. к. rarity_mutation = 100
                chromosome = mutation_multiple_point(chromosome)

            # Запоминаем полученный геном ребенка
            childs.append(chromosome)

        ##
        #  Step 4. Инверсия
        ##
        population = childs

        ##
        #  Step 5. Проверка индекса и фиксация результата
        ##

        # Вывод результатов на консоль
        print(epoch_index, 'f_results', np.copy(f_results).min(axis=0), sep='\t')

        # Выходим из цикла по достижению необходимого числа эпох
        if not epoch_index < epoch:
            break

        # Циклическое инкриминирование эпохи
        epoch_index = epoch_index + 1

    # Выводим график на печать
    plt.grid()
    plt.show()
