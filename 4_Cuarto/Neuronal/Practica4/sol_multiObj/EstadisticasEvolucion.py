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

def configuraEstadisticasEvolucion():

    # Se configura que estadísticas se quieren analizar sobre la evolucion
    stats = tools.Statistics(lambda ind: ind.fitness.values) 
    stats.register("avg", np.mean) 
    stats.register("std", np.std) 
    stats.register("min", np.min) 
    stats.register("max", np.max) 
    
    return stats
    
    #%% Visualizamos una estadística para comprobar como fue la evolucion
def visualizaGrafica(log, population, pareto):
    # Se recuperan los datos desde el log
    gen = log.select("gen")
    avgs = log.select("avg")
    _min = log.select("min")
    _max = log.select("max")
    
    # Se establece una figura para dibujar
    #fig, ax1 = plt.subplots(211)
    #fig, ax2 = plt.subplots(212)
    
    # Se representa el máximo del valor de fitness por cada generación y la media
    ax1 = plt.subplot(121)
    line1 = ax1.plot(gen, _max, "b-", label="Maximo", color="b")
    ax1.set_title(label="Máximo",)
    ax1.set_xlabel("Generation")
    ax1.set_ylabel("Máximo", color="b")

    ax2 = plt.subplot(122)
    line2 = ax2.plot(gen, _min, "r-", label="Minimo", color="r")
    ax2.set_title(label="Minimo",)
    ax2.set_xlabel("Generation")
    ax2.set_ylabel("Minimo", color="b")
     
    plt.plot()
    plt.show()


    # Se establece una figura para dibujar
    fig, ax1 = plt.subplots()

    # Se representa la media del valor de fitness por cada generación
    line1 = ax1.plot(gen, avgs, "g-", label="Media", color="g")
    ax1.set_title(label="Media",)
    ax1.set_xlabel("Generation")
    ax1.set_ylabel("Fitness", color="b")
    
    plt.plot()
    plt.show()

    
    # Se establece una figura para dibujar
    x=[]
    y=[]
    aux=[]
    for i in population:
        aux.append(Evaluacion.fitness(i))
        x.append(aux[0][0])
        y.append(aux[0][1])
        aux.clear()
    xPareto=[]
    yPareto=[]
    aux=[]
    for i in pareto:
        aux.append(Evaluacion.fitness(i))
        xPareto.append(aux[0][0])
        yPareto.append(aux[0][1])
        aux.clear()

    fig, ax1 = plt.subplots()

    # Se representa la media del valor de fitness por cada generación
    line1 = ax1.plot(x, y, "ro", label="Media", color="r")
    line2 = ax1.plot(xPareto,yPareto, "bo", label="Media", color="b")
    ax1.set_title(label="Pareto",)
    ax1.set_xlabel("Puntación")
    ax1.set_ylabel("Tiempo", color="b")
    
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
