"""
Este modulo contiene los metodos necesarios para la evaluación de la adaptación de cada individuo
o solución posible a nuestro problema, indicando cómo de óptima se puede considerar la
respuesta. Esto permite comparar entre posibles soluciones para encontrar cuál será la
más adecuada al problema.

@author: bbaruque
"""

from deap import base, tools, algorithms, creator

import DatosCenso as dc
import ConfiguracionSolucion
import EstadisticasEvolucion as ee
import math
import operator


#%% Funcion de evaluacion
# A partir de un individuo pasado como parametro, permite calcular la adaptacion de dicho individuo
def fitness(toolbox,genotipo):
    resultado = 0
    profundidad = operator.attrgetter("height")
    funcion = toolbox.compile(expr=genotipo)
    for i,j in zip(dc.anos,dc.poblacion):
        resultado += (funcion(i) - j)**2
    return resultado/len(dc.anos), int(profundidad(genotipo))

def fenotipo(toolbox,pareto):
    poblacion_estimada = []
    with open("fenotipo.out","w") as out:
        for i in range(len(pareto)):
            out.write("\n\nIndividuo Pareto:\t"+str(i+1))
            out.write("\nAnio\tPoblacion Estimada\n\n")
            poblacion_estimada = ee.calcularPuntos(toolbox,pareto[i])
            for j in range(len(poblacion_estimada)):
                out.write(str(dc.anos[j])+'\t'+ str(poblacion_estimada[j])+'\n')