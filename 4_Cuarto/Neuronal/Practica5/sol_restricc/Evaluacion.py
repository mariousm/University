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
import math


#%% Funcion de evaluacion
# A partir de un individuo pasado como parametro, permite calcular la adaptacion de dicho individuo
def fitness(toolbox,genotipo):
    resultado = 0
    funcion = toolbox.compile(expr=genotipo)
    for i,j in zip(dc.anos,dc.poblacion):
        resultado += (funcion(i) - j)**2
    return resultado/len(dc.anos),

def fenotipo():
    with open("fenotipo.out","w") as out:
        out.write("Anio\tPoblacion Estimada\n\n")
        for i in range(len(dc.pob_estimada)):
            out.write(str(dc.anos[i])+'\t'+ str(dc.pob_estimada[i])+'\n')