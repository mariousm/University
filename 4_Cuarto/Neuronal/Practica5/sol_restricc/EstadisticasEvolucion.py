# -*- coding: utf-8 -*-
"""
Este módulo permite incorporar medidas estadísticas al proceso evolutivo, que luego se pueden recuperar.
Esto permite estimar si la evolución progresa de forma correcta o no.

@author: bbaruque
"""

import matplotlib.pyplot as plt
import numpy as np 

from deap import base, tools

import CicloEvolutivo
import DatosCenso as dc

def configuraEstadisticasEvolucion():

    # Se configura que estadísticas se quieren analizar sobre la evolucion
    stats = tools.Statistics(lambda ind: ind.fitness.values) 
    stats.register("avg", np.mean) 
    stats.register("std", np.std) 
    stats.register("min", np.min) 
    stats.register("max", np.max) 
    
    return stats

def calcularPuntos(toolbox):
    funcion = toolbox.compile(expr=dc.mejor_genotipo)
    for i in dc.anos:
        dc.pob_estimada.append(funcion(i))


    #%% Visualizamos una estadística para comprobar como fue la evolucion
def visualizaGrafica(log,toolbox):

    calcularPuntos(toolbox)

    # Se recuperan los datos desde el log
    ax1 = plt.subplot()
    linea1 = ax1.plot(dc.poblacion, "b-", label="Poblacion", color="b")
    linea2 = ax1.plot(dc.pob_estimada, "b-", label="Poblacion", color="r")
    lista_aux = []
    for i in range(len(dc.anos)):
        if i%5 != 0:
            lista_aux.append('')
        else:
            lista_aux.append(dc.anos[i])
    plt.xticks(range(len(dc.anos)),lista_aux)
    ax1.legend(('Población Real', 'Población Estimada'),prop = {'size':10}, loc = 'upper left')
    ax1.set_title(label="Estimación población",)
    ax1.set_xlabel("Años")
    ax1.set_ylabel("Población")
    plt.plot()
    plt.show()

    
    ax1 = plt.subplot()
    linea1 = ax1.plot(dc.pob_estimada, "b-", label="Poblacion", color="r")
    lista_aux = []
    for i in range(len(dc.anos)):
        if i%5 != 0:
            lista_aux.append('')
        else:
            lista_aux.append(dc.anos[i])
    plt.xticks(range(len(dc.anos)),lista_aux)
    ax1.set_title(label="Estimación población estimada",)
    ax1.set_xlabel("Años")
    ax1.set_ylabel("Población Estimada")
    plt.plot()
    plt.show()

if __name__ == "__main__":
    # Herramienta para guardar la configuracion de la ejecucion
    toolbox = base.Toolbox()
    # Se configura que estadísticas se quieren analizar sobre la evolucion
    stats = configuraEstadisticasEvolucion()
    # Se ejecuta la evolución y se recupera el log de datos de lo que ha sucedido en la evolucion
    log = CicloEvolutivo.realizaEvolucion(toolbox, stats)
    visualizaGrafica(log)
