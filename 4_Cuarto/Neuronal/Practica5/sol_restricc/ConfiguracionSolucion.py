# -*- coding: utf-8 -*-
"""
Fichero que incluye un ejemplo de las operaciones neesarias para configurar 
la codificación de las posibles soluciones con las que trabajará el algoritmo evolutivo
para resolver el problema.

Se necesita indicar cómo se definen los individuos, el fitness y la población de soluciones
y registrar todas estas opciones en las estructuras que proporciona la librería DEAP

@author: bbaruque
"""

import random

from deap import base, creator, gp
from deap import tools

import LecturaDatos
import operator
import math


def configuraPoblacion(toolbox,pset):

	''' Se configura el fitness que se va a emplear en los individuos
	 En este caso se configura para:
	 1.buscar un único objetivo: es una tupla de solo un numero
	 2.minimizar ese objetivo (se multiplica por un num. positivo)'''
	creator.create("FitnessMin", base.Fitness, weights=(-1.0,))

	''' Se configura el individuo para que utilice la descripción anterior
	de fitness dentro de los individuos '''
	creator.create("Individual", gp.PrimitiveTree, fitness=creator.FitnessMin)

	toolbox.register("expr", gp.genHalfAndHalf, pset=pset, min_=1, max_=2)
	toolbox.register("individual", tools.initIterate, creator.Individual, toolbox.expr)
	toolbox.register("population", tools.initRepeat, list, toolbox.individual)
	toolbox.register("compile", gp.compile, pset=pset)
