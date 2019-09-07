# -*- coding: utf-8 -*-
"""
Este modulo contiene las funciones necesarias para llamar a las funciones de los modulos anteriores
y configurar y lanzar una ejecución de un algoritmo evolutivo que permita alcanzar la
solución óptima al problema de la mochila.

@author: bbaruque
"""

from deap import base, tools, creator
from deap import algorithms

import ConfiguracionSolucion
import Evaluacion
import LecturaDatos
import DatosViaje as dv

#%% Se Define la configuracion del algoritmo genetico
def configuracionAlgoritmo(toolbox):  
    # Se seleccionan procedimiento standard para cruce, mutacion y seleccion
    toolbox.register("mate", tools.cxTwoPoint)
    toolbox.register("mutate", tools.mutShuffleIndexes, indpb=0.2)
    toolbox.register("select", tools.selTournament, tournsize=3)
	 # Se define cómo se evaluará cada individuo
	 # En este caso, se hará uso de la función de evaluación que se ha definido en el modulo Evaluacion.py
    toolbox.register("evaluate", Evaluacion.fitness)
    toolbox.decorate("evaluate", tools.DeltaPenality(Evaluacion.esValido, 0, Evaluacion.distancia))

#%% Se define como se realiza la Evolución de la busqueda de la solución
def realizaEvolucion(toolbox, stats):
    LecturaDatos.carga_datos('.\\Ficheros\\set_64_1\\set_64_1_15.txt')
    # Se configura cómo se define cada individuo. Ver fichero correspondiente
    ConfiguracionSolucion.configuraPoblacion(toolbox)

    configuracionAlgoritmo(toolbox)

    # Se inicializa la poblacion con 300 individuos
    population = toolbox.population(n=300)
    # Se llama al algoritmo que permite la evolucion de las soluciones
    population, logbook = algorithms.eaSimple(population, toolbox, 
	                               cxpb=0.5, mutpb=0.2, # Probabilidades de cruce y mutacion
	                               ngen=20, verbose=False, stats=stats) # Numero de generaciones a completar y estadisticas a recoger

    print(logbook)

    # Comprobamos cual es la mejor solucion encontrada por evolucion
    print("La mejor solucion encontrada es: ")
    print(tools.selBest(population,1)[0])
    
    return logbook

if __name__ == "__main__":
    # Herramienta para guardar la configuracion de la ejecucion
    toolbox = base.Toolbox()
    realizaEvolucion(toolbox,[])