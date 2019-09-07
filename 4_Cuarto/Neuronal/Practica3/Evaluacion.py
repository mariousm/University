"""
Este modulo contiene los metodos necesarios para la evaluación de la adaptación de cada individuo
o solución posible a nuestro problema, indicando cómo de óptima se puede considerar la
respuesta. Esto permite comparar entre posibles soluciones para encontrar cuál será la
más adecuada al problema.

@author: bbaruque
"""

import numpy as np

from deap import base

import DatosGoogle as dg
import ConfiguracionSolucion


def penalizar_genotipo(genotipo):
    suma_pen = 1
    for i in range(dg.caches):
        desplazamiento = i*dg.videos
        tamano = 0
        for j in range(dg.videos):
            tamano+=dg.memorias[j]*genotipo[desplazamiento+j]
        resta = tamano - dg.tam_cache
        if resta > 0:
            suma_pen+=resta/dg.tam_cache
    if suma_pen != 1:
        suma_pen*=100
    return suma_pen

#%% Funcion de evaluacion
# A partir de un individuo pasado como parametro, permite calcular la adaptacion de dicho individuo
def fitness(genotipo):
    mstotal = 0
    for i in range(dg.endpoints):
        for j in range(dg.videos):
            peticion = dg.descripcion_request[i][j]
            if peticion != 0:
                conjunto_latencias = set()
                for c in range(dg.caches):
                    latencia = dg.endpointcache[i][c]
                    if latencia != 0:
                        if genotipo[c*dg.videos+j] == 1:
                            conjunto_latencias.add(latencia)
                if len(conjunto_latencias) > 0:
                    mstotal+=min(conjunto_latencias)*peticion
                else:
                    mstotal+=dg.tiempo_endpoint_central[i]*peticion
    mstotal*=penalizar_genotipo(genotipo)
    return mstotal,

def fenotipo(genotipo):

    fenotipo = []
    for i in range(dg.caches):
        fenotipo.append([])
        for j in range(dg.videos):
            if genotipo[i*dg.videos+j] == 1:
                fenotipo[i].append(j)
    
    with open("fenotipo.out",'w') as out:
        count = 0
        for i in fenotipo:
            if len(i) != 0:
                count += 1
        out.write(str(count)+"\n")
        for i in range(len(fenotipo)):
            if len(fenotipo[i]) != 0:
                out.write(str(i))
                for j in fenotipo[i]:
                    out.write(" "+str(j))
                out.write("\n")

#%% Se comprueba la asignacion de fitness:
# Esta parte solo se incluye como comprobacion de la función evalKnapsack
# No es necesario incluirlo en la evaluacion

def prueba():

    #Herramienta para guardar la configuracion de la poblacion
    toolbox = base.Toolbox()

    ConfiguracionSolucion.configuraPoblacion(toolbox)

    # Se instancia un individuo (aleatorio)
    ind = toolbox.individual()
    
    '''
    Se aconseja al alumno probar con varios individuos en diferentes condiciones
    de optimalidad para comprobar si la función está bien definida en todo el 
    espacio de búsqueda.    
    '''

    # Se imprime el individuo ANTES de evaluar
    print (ind)
    print (ind.fitness.valid) # False

    ind.fitness.values = fitness(ind)

    # Se imprime el individuo DESPUES de evaluar
    print (ind.fitness.valid) # True
    print (ind.fitness)

if __name__ == "__main__":
    prueba()
