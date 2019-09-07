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

import DatosCenso as dc

def carga_datos(ruta_fichero):
    dc.init()
    with open(ruta_fichero, 'r') as fichero:
        fichero.readline()
        while True:
            linea = fichero.readline()
            if not linea:
                break
            aux = linea.split('\t')
            aux1 = aux[0].split(' ')

            if len(aux1) == 2:
                if aux1[0] != 'Junio':
                    dc.anos.append(int(aux1[1]))
                    dc.poblacion.append(int(aux[4]))
            else:
                dc.anos.append(int(aux[0]))
                dc.poblacion.append(int(aux[4]))
    dc.anos.sort()
    dc.poblacion.reverse()
    
""" if __name__ == "__main__":
    carga_datos('.\\Ficheros\\JapanCensus_2017.csv')
    print(dc.anos)
    print(dc.poblacion) """