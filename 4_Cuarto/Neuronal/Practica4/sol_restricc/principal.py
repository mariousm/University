import LecturaDatos as ld
import EstadisticasEvolucion as ee
import CicloEvolutivo as ce
import ConfiguracionSolucion as cs
import Evaluacion

from deap import base, tools, algorithms, creator

def experimento():
    toolbox = base.Toolbox()
    cs.configuraPoblacion(toolbox)
    ce.configuracionAlgoritmo(toolbox)

    alg_param = {}
    alg_param['cxpb'] = 0.75
    alg_param['mutpb'] = 0.2
    alg_param['pop_size'] = 25
    alg_param['ngen'] = 100


    init_pop = toolbox.population(n=100)

    ind = []
    for individuo in init_pop:
        individuo = creator.Individual(list(dict.fromkeys(individuo).keys()))
        ind.append(individuo)
    init_pop = ind
    
    # Ejecución de la evolucion
    population, logbook = algorithms.eaSimple(
        init_pop, toolbox, 
        cxpb=alg_param['cxpb'], 
        mutpb=alg_param['mutpb'], 
        ngen=alg_param['ngen'], 
        verbose=True, stats=ee.configuraEstadisticasEvolucion())

    return population, logbook

def leer_fichero():
    print("¿Cómo se llama el fichero?")
    nombre = input()
    return nombre




if __name__=="__main__":
    #ld.carga_datos('C:\\Users\\mario\\Desktop\\Admin\\UBU\\2018-19\\PrimerSemestre\\Neuronal\\Practica3\\CNE_ejemplos-master\\CNE_ejemplos-master\\datos_videos2017.in\\streaming\\me_at_the_zoo.in')
    fichero = leer_fichero()
    ld.carga_datos('.\\Ficheros\\'+fichero)

    population, logbook = experimento()
    genotipo = tools.selBest(population, 1)[0]
    print("")
    print("Penalización Mejor Genotipo: " + str(Evaluacion.penalizar_genotipo(genotipo)))
    print("")
    print("Fitness Mejor Genotipo" + str(Evaluacion.fitness(genotipo)))
    Evaluacion.fenotipo(genotipo)
    ee.visualizaGrafica(logbook)


