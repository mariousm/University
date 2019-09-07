from ipywidgets import HTML, Dropdown ,Button, HBox, VBox, Box

"""
Clase que define los componentes gráficos de la interfaz

"""
class gui:
    
    
    def __init__(self, manual=True):
        """ Constructor por defecto

        Devuelve un objeto que crea la interfaz de usuario

        Parámetros:
        manual -- True (defecto) crea la interfaz para modo manual, false crea para modo automático
        """ 
        self.manual = manual

    def get_ui_elements(self):
        """ Obtiene los componentes gráficos del juego

        Devuelve un contenedor con los botones y el visor del juego
        
        Parámetros: 
        Ninguno
        """
        
        # visor HTML donde se representará el juego
        visor=HTML()

        # Crea un desplegable con los niveles
        desplegable = Dropdown(description = 'Elija nivel:')

        # Botones para las direcciones
        up = Button(description="^")
        down = Button(description="v")
        right = Button(description=">")
        left = Button(description="<")

        empty=Button(description=" ")
        empty.margin=2
        
        

        direcciones=VBox([HBox([empty,up,empty]),HBox([left,down,right])])
        acciones = self.get_actions()
        
        control = VBox([direcciones,acciones])
        
        ui=VBox(children=[desplegable, visor, control])

        return ui
    
    def get_actions(self):
        """         
        devuelve los botones de acción 
         en modo manual solo: reiniciar
         en modo automático: reiniciar, resolver, sig, anterior
        
        Parámetros: 
        Ninguno
        """       
        
        botones_accion = []
       
        reiniciar = Button(description="Reiniciar")
        botones_accion.append(reiniciar)
        
        if not self.manual:    
            
            resolver = Button(description="Resolver")
            siguiente = Button(description="Siguiente")
            anterior = Button(description="Anterior")
            botones_accion.append(resolver)
            botones_accion.append(siguiente)
            botones_accion.append(anterior)
            
            
        accciones = HBox(botones_accion)
        
        return accciones


def pinta_juegoHTML(level,state):
    """ 
    Obtiene la representación gráfica del juego en formato HTML
        
    Parámetros: 
    level- un nivel
    state - un estado
    """

    tablero = level.get_tablero()
    destinos = level.get_destinos()

    jugador = state.get_jugador()
    cajas = state.get_cajas()

    
    alto = len(tablero)
    ancho = len(tablero[0])

    cadenaHTML = '<style>table {border: 0px; text-align: center; text-decoration: none; '
    cadenaHTML += 'font-size: 11px; margin: 3px;} td{border: 0px solid black; height: 50px; width: 50px; padding: 0;} '    
    cadenaHTML += '</style>'

    cadenaHTML += '<div class="row">'
    cadenaHTML += '<br>'
    cadenaHTML += '<div class="col-md-10">'
    cadenaHTML += '<table> <tr>'


    caracter = '<td></td>'
    blockImg = '<td><img src="./sprites/block.jpg" alt="" border=0 height=50 width=50></img></td>'
    huecoImg = '<td><img src="./sprites/hueco.jpg" alt="" border=0 height=50 width=50></img></td>'
    cajasImg = '<td><img src="./sprites/caja.jpg" alt="" border=0 height=50 width=50></img></td>'     
    destinosImg = '<td><img src="./sprites/destino.jpg" alt="" border=0 height=50 width=50></img></td>'     
    jugadorImg = '<td><img src="./sprites/hombre.jpg" alt="" border=0 height=50 width=50></img></td>'

    saltoLinea = "</tr>"

    for i in range(alto):
        for j in range(ancho):
            coord = (i,j)

            if not tablero[coord[0]][coord[1]] ==0:
                caracter = blockImg
            elif tuple(jugador) == coord:
                caracter = jugadorImg
            elif coord in cajas:                
                caracter = cajasImg
            elif coord in destinos:
                caracter = destinosImg
            else:
                caracter = huecoImg

            cadenaHTML += caracter
        cadenaHTML += saltoLinea


    return cadenaHTML



