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
import Evaluacion
import DatosCenso as dc

def configuraEstadisticasEvolucion():

    # Se configura que estadísticas se quieren analizar sobre la evolucion
    stats = tools.Statistics(key=lambda ind: ind.fitness.values)
    stats.register("avg", np.mean, axis=0)
    stats.register("std", np.std, axis=0)
    stats.register("min", np.min, axis=0)
    stats.register("max", np.max, axis=0) 
    
    return stats

def calcularPuntos(toolbox,individuo):
    puntos_estimados = []
    funcion = toolbox.compile(expr=individuo)
    for i in dc.anos:
        puntos_estimados.append(funcion(i))
    return puntos_estimados

    #%% Visualizamos una estadística para comprobar como fue la evolucion
def visualizaGrafica(log,toolbox,population,pareto):
    ''' 
    for i in pareto:
        puntos_estimados = calcularPuntos(toolbox,i)

        # Se recuperan los datos desde el log
        ax1 = plt.subplot()
        linea1 = ax1.plot(dc.poblacion, "b-", label="Poblacion", color="b")
        linea2 = ax1.plot(puntos_estimados, "b-", label="Poblacion", color="r")
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
        linea1 = ax1.plot(puntos_estimados, "b-", label="Poblacion", color="r")
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
    '''
    # Se establece una figura para dibujar
    x=[]
    y=[]
    aux=[]
    for i in population:
        aux.append(Evaluacion.fitness(toolbox,i))
        x.append(aux[0][0])
        y.append(aux[0][1])
        aux.clear()
    xPareto=[]
    yPareto=[]
    aux=[]
    for i in pareto:
        aux.append(Evaluacion.fitness(toolbox,i))
        xPareto.append(aux[0][0])
        yPareto.append(aux[0][1])
        aux.clear()

    fig, ax1 = plt.subplots()

    # Se representa la media del valor de fitness por cada generación
    line1 = ax1.plot(x, y, "ro", label="Media", color="r")
    line2 = ax1.plot(xPareto,yPareto, "bo", label="Media", color="b")
    ax1.set_title(label="Pareto",)
    ax1.set_xlabel("Error Cuadrático Medio")
    ax1.set_ylabel("Profundidad")
    
    plt.plot()
    plt.show()
