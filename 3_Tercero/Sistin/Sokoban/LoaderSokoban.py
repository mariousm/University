from os import listdir
from os.path import isfile, join

from Sokoban import Level, State

"""
Clase utilizada para cargar niveles a partir de su representación en ficheros de texto

"""
class Loader:
    
    
    def get_all_levels(self):
        """ Obtiene la lista de niveles disponibles
        que son todos aquellos contenidos en la carpeta /levels/

        Devuelve una lista con los manejadores a ficheros contenidos en la carpeta de niveles 
        
        Parámetros: 
        Ninguno
        """
        
        mypath='./levels/'
        onlyfiles = [f for f in listdir(mypath) if f.startswith('level') and f.endswith('.txt') and isfile(join(mypath, f))]
        onlyfiles.sort()
        return onlyfiles
    
    
    def carga_nivel(self,str_level):
        """ Carga un nivel a partir de un texto

        Devuelve un objeto de tipo State y otro de tipo Level
        
        Parámetros: 
        str_level - un string con la codificación del nivel
        """
        nivel = str_level.split("\n")

        tablero = []
        cajas = set()
        destinos = set()

        i =0
        for fila in nivel:
            tabFila = []
            j =0
            for caracter in fila:
                if caracter == "#":
                    tabFila.append(1)
                else:
                    tabFila.append(0)


                if caracter in {'@', '+'}:
                    jugador = [i,j]            

                if caracter in {'$', '*'}:
                    cajas.add((i,j))
                if caracter in {'.', '*', '+'}:
                    destinos.add((i,j))

                j+=1
            i+=1
            if len(fila)>0:
                tablero.append(tabFila)

        return Level(tablero,frozenset(destinos)),State(jugador,cajas)
    
