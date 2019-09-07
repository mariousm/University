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

import DatosViaje as dv

def carga_datos(ruta_fichero):
    dv.init()
    with open(ruta_fichero, 'r') as fichero:
        ciudad = -1
        #primera linea
        firstline = fichero.readline()
        aux = firstline.split('\t')
        dv.tiempo = int(aux[0])

        #segunda linea
        secondline = fichero.readline()
        aux = secondline.split('\t')
        ciudad += 1
        dv.punto_inicio.append(ciudad)
        dv.punto_inicio.append(float(aux[0]))
        dv.punto_inicio.append(float(aux[1]))
        dv.punto_inicio.append(float(aux[2]))

        #Tercera linea
        thirdline = fichero.readline()
        aux3 = thirdline.split('\t')
        
        while True:
            lista = []
            linea = fichero.readline()
            if not linea:
                break
            aux = linea.split('\t')
            ciudad += 1
            lista.append(ciudad)
            lista.append(float(aux[0]))
            lista.append(float(aux[1]))
            lista.append(float(aux[2]))
            dv.punto_visita.append(lista)
            
        ciudad += 1
        dv.punto_fin.append(ciudad)
        dv.punto_fin.append(float(aux3[0]))
        dv.punto_fin.append(float(aux3[1]))
        dv.punto_fin.append(float(aux3[2]))
        

""" if __name__ == "__main__":
    carga_datos('.\\Ficheros\\set_64_1\\set_64_1_15.txt')
    print(dv.tiempo)
    print(dv.punto_inicio)
    print(dv.punto_fin)
    print(dv.punto_visita) """