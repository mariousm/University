"""
Este modulo contiene los metodos necesarios para la evaluación de la adaptación de cada individuo
o solución posible a nuestro problema, indicando cómo de óptima se puede considerar la
respuesta. Esto permite comparar entre posibles soluciones para encontrar cuál será la
más adecuada al problema.

@author: bbaruque
"""

from deap import base, tools, algorithms, creator

import DatosViaje as dv
import ConfiguracionSolucion
import math


def penalizar_genotipo(genotipo):
    suma_pen = 1

    tiempo_genotipo = tiempoGenotipo(genotipo)
    resta = tiempo_genotipo - dv.tiempo

    if resta > 0:
        suma_pen+=resta/dv.tiempo
    if suma_pen != 1:
        suma_pen*=1
    return suma_pen, resta*-1


#%% Funcion de evaluacion
# A partir de un individuo pasado como parametro, permite calcular la adaptacion de dicho individuo
def fitness(genotipo):
    ind = []
    for i in genotipo:
        ind = creator.Individual(list(dict.fromkeys(genotipo).keys()))

    puntuacion = 0
    for visitado in ind:
        for i in dv.punto_visita:
            if i[0] == visitado:
                puntuacion+=i[3]
    suma_pen, resta = penalizar_genotipo(ind)
    #puntuacion/= suma_pen
    return puntuacion, resta

def tiempoGenotipo(genotipo):
    ind = []
    for i in genotipo:
        ind = creator.Individual(list(dict.fromkeys(genotipo).keys()))

    primero = ind[0]
    ultimo = ind[-1]
    pre = None
    km = 0
    for visitado in ind:
        for i in dv.punto_visita:
            if i[0] == visitado:
                temp = i
                break
        
        if temp[0] == primero:
            km += math.sqrt(abs(temp[1]-dv.punto_inicio[1])**2+abs(temp[2]-dv.punto_inicio[2])**2)
            pre = temp
        elif temp[0] == ultimo:
            km += math.sqrt(abs(dv.punto_fin[1]-temp[1])**2+abs(dv.punto_fin[2]-temp[2])**2)
            pre = temp
        else:
            km += math.sqrt(abs(temp[1]-pre[1])**2+abs(temp[2]-pre[2])**2)
            pre = temp
    return km/5

def fenotipo(pareto):
    ind = []
    for i in pareto:
        ind.append(creator.Individual(list(dict.fromkeys(i).keys())))
    
    fenotipo = []
    with open("fenotipo.out", "w") as out:
        for i in ind:
                out.write(str(dv.tiempo)+"\t"+str(tiempoGenotipo(i))+"\n")
                out.write("\t"+str(dv.punto_inicio[0])+"\n")
                for j in range(len(i)):
                    #fenotipo.append((j,i[j]))
                    out.write(str(j)+"\t"+str(i[j])+"\n")
                out.write("\t"+str(dv.punto_fin[0])+"\n")
                out.write("----------------"+"\n\n\n")                    

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
