import LecturaDatos as ld
import EstadisticasEvolucion as ee
import CicloEvolutivo as ce
import ConfiguracionSolucion as cs
import Evaluacion
import DatosCenso as dc

from deap import base, tools, algorithms, creator, gp
import operator, math, random

def condicionParada(population, toolbox, cxpb, mutpb, ngen, stats=None,
             halloffame=None, verbose=__debug__):

    logbook = tools.Logbook()
    logbook.header = ['gen', 'nevals'] + (stats.fields if stats else [])

    # Evaluate the individuals with an invalid fitness
    invalid_ind = [ind for ind in population if not ind.fitness.valid]
    fitnesses = toolbox.map(toolbox.evaluate, invalid_ind)
    for ind, fit in zip(invalid_ind, fitnesses):
        ind.fitness.values = fit

    if halloffame is not None:
        halloffame.update(population)

    record = stats.compile(population) if stats else {}
    logbook.record(gen=0, nevals=len(invalid_ind), **record)
    if verbose:
        print(logbook.stream)

    lista_aux = []
    # Begin the generational process
    #for gen in range(1, ngen + 1):
    gen = 1
    while True:
        
        # Select the next generation individuals
        offspring = toolbox.select(population, len(population))

        # Vary the pool of individuals
        offspring = algorithms.varAnd(offspring, toolbox, cxpb, mutpb)

        # Evaluate the individuals with an invalid fitness
        invalid_ind = [ind for ind in offspring if not ind.fitness.valid]
        fitnesses = toolbox.map(toolbox.evaluate, invalid_ind)
        for ind, fit in zip(invalid_ind, fitnesses):
            ind.fitness.values = fit

        # Update the hall of fame with the generated individuals
        if halloffame is not None:
            halloffame.update(offspring)

        # Cambiamos la condicion de parada, cuando no se mejora en x generaciones consecutivas, paramos el algoritmo
        if (Evaluacion.fitness(toolbox,tools.selBest(population, 1)[0]) in lista_aux) or len(lista_aux)==0:
            lista_aux.append(Evaluacion.fitness(toolbox,tools.selBest(population, 1)[0]))
        else:
            lista_aux = []
            lista_aux.append(Evaluacion.fitness(toolbox,tools.selBest(population, 1)[0]))
        # Replace the current population by the offspring
        population[:] = offspring

        # Append the current generation statistics to the logbook
        record = stats.compile(population) if stats else {}
        logbook.record(gen=gen, nevals=len(invalid_ind), **record)
        if verbose:
            print(logbook.stream)
        gen += 1
        if len(lista_aux) == 10:
            break

    return population, logbook


def protectedDiv(left, right):
	try:
		return left / right
	except ZeroDivisionError:
		return 1

def experimento():
    toolbox = base.Toolbox()

    pset = gp.PrimitiveSet("MAIN", 1)
    pset.addPrimitive(operator.add, 2)
    pset.addPrimitive(operator.sub, 2)
    pset.addPrimitive(operator.mul, 2)
    pset.addPrimitive(protectedDiv, 2)
    pset.addPrimitive(operator.neg, 1)
    pset.addPrimitive(math.cos, 1)
    pset.addPrimitive(math.sin, 1)
    pset.addEphemeralConstant("rand101", lambda: random.randint(-1,1))
    pset.renameArguments(ARG0='x')
    
    cs.configuraPoblacion(toolbox, pset)
    ce.configuracionAlgoritmo(toolbox, pset)

    alg_param = {}
    alg_param['cxpb'] = 0.75
    alg_param['mutpb'] = 0.2
    alg_param['pop_size'] = 25
    alg_param['ngen'] = 100


    init_pop = toolbox.population(n=len(dc.anos))
    
    # Ejecución de la evolucion
    population, logbook = condicionParada(
        init_pop, toolbox, 
        cxpb=alg_param['cxpb'], 
        mutpb=alg_param['mutpb'], 
        ngen=alg_param['ngen'], 
        verbose=True, stats=ee.configuraEstadisticasEvolucion()
    )

    pareto = tools.ParetoFront()
    pareto.update(population)
    for i in pareto:
        print(ee.calcularPuntos(toolbox,i))

    return population, logbook, toolbox, pareto

def leer_fichero():
    print("¿Cómo se llama el fichero?")
    nombre = input()
    return nombre




if __name__=="__main__":
    fichero = leer_fichero()
    ld.carga_datos('.\\Ficheros\\'+fichero)

    population, logbook, toolbox, pareto = experimento()
    dc.mejor_genotipo = tools.selBest(population, 1)[0]
    print("")
    #print("Penalización Mejor Genotipo: " + str(Evaluacion.penalizar_genotipo(genotipo)))
    print("")
    #print("Fitness Mejor Genotipo" + str(Evaluacion.fitness(toolbox,dc.mejor_genotipo)))
    ee.visualizaGrafica(logbook,toolbox,population,pareto)
    Evaluacion.fenotipo(toolbox, pareto)


