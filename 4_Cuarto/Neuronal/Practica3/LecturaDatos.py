# -*- coding: utf-8 -*-
"""
Created on Wed Oct 18 12:25:46 2017

@author: bbaruque
"""
"""
Más información de las funciones en:
    
    https://docs.python.org/3/library/csv.html
    http://www.pythonforbeginners.com/files/reading-and-writing-files-in-python
    
"""

from random import randint as rint

from deap import base, tools
from deap import algorithms

import DatosGoogle as dg

def carga_datos(ruta_fichero):
    dg.init()
    with open(ruta_fichero, 'r') as fichero:
        #primera linea
        datos = fichero.readlines()
        firstline = datos[0].split(' ')
        dg.videos = int(firstline[0])
        dg.endpoints = int(firstline[1])
        dg.request = int(firstline[2])
        dg.caches = int(firstline[3])
        dg.tam_cache = int(firstline[4])

        #segunda linea
        dg.memorias = list(map(int,datos[1].split(' ')))

        #dg.endpoints cache
        desplazamiento = 2
        for i in range(dg.endpoints):
            endata = list(map(int, datos[desplazamiento].split(' ')))
            dg.tiempo_endpoint_central.append(endata[0])
            dg.endpointcache.append([0]*dg.caches)
            for j in range(1,endata[1]+1):
                cachedata = list(map(int, datos[j+desplazamiento].split(' ')))    
                dg.endpointcache[i][cachedata[0]] = cachedata[1]
            desplazamiento+=endata[1]+1

        #peticiones dg.request
        for j in range(dg.endpoints):
            dg.descripcion_request.append([0]*dg.videos)
        for i in range(dg.request):
            requestdata = list(map(int,datos[desplazamiento+i].split(' ')))
            dg.descripcion_request[requestdata[1]][requestdata[0]] = requestdata[2]

""" def generar_genotipos(pob_inicial = 100):
    genotipos = []
    for i in range(pob_inicial):
        gen = []
        for j in range(dg.caches*dg.videos):
            gen.append(rint(0,1))
        genotipos.append(gen)
    return genotipos """