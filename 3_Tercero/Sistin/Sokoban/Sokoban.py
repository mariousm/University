"""
Define un estado del juego Sokoban

Las partes actualizables del juego como el jugador y las cajas
"""
class State:
    def __init__(self,jugador,cajas):
        """ Constructor por defecto

        Devuelve un objeto de tipo State

        Parámetros:
        jugador -- coordenadas del jugador en forma de lista
        cajas -- set de tuplas de coordenadas de las cajas
        """  
        self.jugador = jugador
        self.cajas = cajas
        
    def get_jugador(self):
        """ Obtiene las coordenadas del jugador

        Devuelve lista con las coordenadas y,x del jugador

        Parámetros:
        Ninguno
        """   
        return self.jugador
    
    def get_cajas(self):
        """ Obtiene las coordenadas de las cajas

        Devuelve un set con y,x de cada una de las cajas, cada caja una tupla

        Parámetros:
        Ninguno
        """   
        return self.cajas
    
    def __str__(self):
        """ Obtiene la representación del objeto 

        Devuelve un string con la representación del objeto

        Parámetros:
        Ninguno
        """   
        return str(self.jugador)+str(self.cajas)
    
    def __repr__(self):
        """ Obtiene la representación del objeto 

        Devuelve un string con la representación del objeto

        Parámetros:
        Ninguno
        """  
        return str(self.jugador)+str(self.cajas)
    
    def __eq__(self,other):
        return self.jugador == other.jugador and self.cajas == other.cajas
    
    
    def __hash__(self):
        return hash((tuple(self.jugador), frozenset(self.cajas)))
    
    
"""
Define un nivel del juego Sokoban

Las partes no-actualizables del juego como el el tablero y los destinos de cajas
"""    
class Level:
    
    def __init__(self,tablero,destinos):
        """ Constructor por defecto

        Devuelve un objeto de tipo Level

        Parámetros:
        tablero -- lista de listas (lista 2D) que representa casillas libres y muros
        destinos -- frozenset de tuplas de coordenadas de los destinos
        """  
        self.tablero = tablero
        self.destinos = destinos
        
    def get_tablero(self):
        """ Devuelve el tablero

        Devuelve lista 2d, 0 en las posiciones libres, 1 en las posiciones donde hay un muro

        Parámetros:
        Ninguno
        """ 
        return self.tablero
    
    def get_destinos(self):
        """ Obtiene las coordenadas de los destinos

        Devuelve un frozenset con y,x de cada una de los destinos, cada destino una tupla

        Parámetros:
        Ninguno
        """   
        return self.destinos
    
    
    def __str__(self):
        """ Obtiene la representación del objeto 

        Devuelve un string con la representación del objeto

        Parámetros:
        Ninguno
        """   
        return str(self.tablero)+str(self.destinos)
    
    def __repr__(self):
        """ Obtiene la representación del objeto 

        Devuelve un string con la representación del objeto

        Parámetros:
        Ninguno
        """   
        return str(self.tablero)+str(self.destinos)
