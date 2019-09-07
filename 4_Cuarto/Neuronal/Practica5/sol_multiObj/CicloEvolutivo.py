# -*- coding: utf-8 -*-
"""
Este modulo contiene las funciones necesarias para llamar a las funciones de los modulos anteriores
y configurar y lanzar una ejecución de un algoritmo evolutivo que permita alcanzar la
solución óptima al problema de la mochila.

@author: bbaruque
"""

from deap import base, tools, creator, gp
from deap import algorithms

import ConfiguracionSolucion
import Evaluacion
import LecturaDatos
import operator

#%% Se Define la configuracion del algoritmo genetico
def configuracionAlgoritmo(toolbox,pset):

    toolbox.register("select", tools.selNSGA2)
    toolbox.register("mate", gp.cxOnePoint)
    toolbox.register("expr_mut", gp.genFull, min_=0, max_=2)
    toolbox.register("mutate", gp.mutUniform, expr=toolbox.expr_mut, pset=pset)

    toolbox.decorate("mate", gp.staticLimit(key=operator.attrgetter("height"), max_value=17))
    toolbox.decorate("mutate", gp.staticLimit(key=operator.attrgetter("height"), max_value=17))

    toolbox.register("evaluate", Evaluacion.fitness, toolbox)
    #toolbox.decorate("evaluate", tools.DeltaPenality(Evaluacion.esValido, 0, Evaluacion.distancia))

