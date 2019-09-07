 /*
	autores: Mario Ubierna San Mamés
		 Jorge Navarro González
	fecha: 11/10/2017
	version: 1.0
 */

%{
  //includes y defines
  #include<stdio.h>
  #include<string.h>
  #include<stdlib.h>
  #include<stdbool.h>
  #define CP(FROM,TO) free(TO);TO=strdup(FROM)

  char *tipoGraph,*nombreGrafo; //la primera es para el tipo del grafo y la segunda para guardar el nombre del grafo
  char *subgrafoActual,*subgrafoLargo; //para saber el nombre del subgrafo actual y la otra para guardar en ella el subgrafo de nombre mas largo
  char *nombreNodoActual,*nombreNodo; //guardamos el nombre del nodo actual y en la otra el nombre del nodo mas largo
  char *etiquetaactual, *etiquetalarga; //guardamos la etiqueta actual y la etiqueta mas larga
  bool tiposhape=false; //flag para saber si el nodo tiene la etiqueta shape
  bool etiqueta=false; //flag para saber si el nodo tiene la etiqueta label
  bool poligono=false; //flag para saber si el nodo tiene la etiqueta polygon
  bool countlados=false; //flag para saber si el nodo tiene la etiqueta sides
  int numladosmayor=0; //para guardar el número de lados del nodo con mayor número de lados
  int numactual; //para guardar el número actual de lados de un nodo
  bool subgrafo=false; //flag para saber si estoy dentro de un subgrafo
  bool soyNode=false; //flag para saber si es un node con la estructura node[]

%}

 //es una llamada a las funciones de la pila
%option stack
 //estos son todos los estados que hemos creado
%x  GRAPHNAME SUBGRAPHNAME  STMTLIST  ATTRSTMT  ASSIG ATTLIST VALPROP VALATT COM1 COM2
 //expresiones regulares que hemos definido
tipoDelGraph    ("graph")|("digraph")
num             [0-9]+
id              (([a-zA-Z_\-\200-\377])([a-zA-Z0-9_\-\200-\377]*))|(\'.+\')|(\".+\")|(\<.+\>)

%%

 //estos son los comentarios

<*>\/\/                               {yy_push_state(COM1); //para guardar en la pila en el estado en el que nos encontrabamos
					                             }
<COM1>"\n"                            {yy_pop_state(); //para volver al estado en el que estabamos
					                             }
<*>"/*"                               yy_push_state(COM2);
<COM2>"*/"                            yy_pop_state();
<COM1>. {;}
<COM2>.|\n {;}


{tipoDelGraph}                       {//aqui comprobamos que sea un grafo dirigido o no dirigido
					                             if(strncmp(yytext,"graph",5)==0){
                                         CP("g",tipoGraph);
                                       }else if(strncmp(yytext,"digraph",7)==0){
                                         CP("d",tipoGraph);
                                       };BEGIN(GRAPHNAME);}

<GRAPHNAME>{id}                       {if(strncmp(tipoGraph,"g",1)==0){
                                        CP(yytext,nombreGrafo); //para guardar el nombre del grafo
                                       }else{
                                        CP(yytext,nombreGrafo); //para guardar el nombre del grafo
                                       };BEGIN(GRAPHNAME);}

<GRAPHNAME>"{"                        BEGIN(STMTLIST);


<STMTLIST>"subgraph"                  {subgrafo=true; //lo ponemos a true para decir que estamos en un subgrafo
                                      BEGIN(SUBGRAPHNAME);}

<SUBGRAPHNAME>{id}                    {CP(yytext,subgrafoActual);
					                             //aqui comprobamos el subgrafo con el nombre mas largo y a continuación lo guardamos
                                       if(!subgrafoLargo || strcmp(subgrafoActual,subgrafoLargo)>0){
                                        CP(subgrafoActual,subgrafoLargo);
                                       };BEGIN(SUBGRAPHNAME);}

<SUBGRAPHNAME>"{"                     BEGIN(STMTLIST);

<STMTLIST>{id}/(" ->")                 {//comprobamos primero si es node[] y si dentro es un "polygon" y si tiene la etiqueta "sides"
					                               if(soyNode==true && poligono==true && countlados==true){
                                          if(numactual==numladosmayor){
                                            CP(yytext,nombreNodo);
                                            soyNode=false;
                                          }
                                          BEGIN(STMTLIST);};}

<STMTLIST>{id}                        {//ponemos todos los flags a false
				                               tiposhape=false;
                                       etiqueta=false;
                                       countlados=false;
                                       poligono=false;
				                               //comprobamos si leemos la etiqueta node y lo ponemos a true
                                       if(strcmp(yytext,"node")==0){
                                        soyNode=true;
                                        BEGIN(ASSIG);
                                       }
				                               //copiamos el contenido del yytext que será el nombre de un nodo en el nodo actual
                                       CP(yytext,nombreNodoActual);
                                       BEGIN(ASSIG);}



<VALPROP>{num}|{id}                   BEGIN(STMTLIST);

<ASSIG>"["                            BEGIN(ATTLIST);

<ASSIG>"="                            {nombreNodoActual=NULL;
                                       BEGIN(VALPROP);}

<ASSIG>"--"                           {nombreNodoActual=NULL;
					                             BEGIN(STMTLIST);}


<VALATT>","|";"                       BEGIN(ATTLIST);

<ATTLIST>"="                          BEGIN(VALATT);

<ATTLIST>{id}		                      {//comprobaciones de todas las etiquetas
					                              if(strncmp(yytext,"shape",5)==0){
                                          tiposhape=true;
                                        }
                                        if(strncmp(yytext,"label",5)==0){
                                          etiqueta=true;
                                        }
                                        if(poligono && strncmp(yytext,"sides",5)==0){
                                          countlados=true;
                                        };BEGIN(ATTLIST);}

<VALATT>{num}|{id}                    {//para saber si la forma es tipo polygon
					                             if(tiposhape==true && strncmp(yytext,"polygon",7)==0){
                                        poligono=true;
                                       }
					                             //para guardar el nodo con mayor numero de lados
                                       if(countlados==true){
                                        numactual=atoi(yytext);
                                        if(numactual>numladosmayor && nombreNodoActual != NULL){
                                          numladosmayor=numactual;
					                                CP(nombreNodoActual,nombreNodo);
                                        }
                                       }
					                             //para guardar la etiqueta mas larga
                                       if(etiqueta==true){
					                                CP(yytext,etiquetaactual);
                                        if(!etiquetalarga || strcmp(etiquetaactual,etiquetalarga)>0){
                                          CP(etiquetaactual,etiquetalarga);
                                        }
                                       };BEGIN(VALATT);}

<VALATT>"]"                           {BEGIN(STMTLIST);}

<STMTLIST>"}"                         {//para saber si la llave de cierre es para acabar un subgrafo o para acabar todo el grafo entero
					                             if(subgrafo==true){
                                         subgrafo=false;
                                         BEGIN(STMTLIST);
                                       }else{
                                        yywrap();
                                      };}

<*>\n|.                               {;}
%%

int main(int argc, char **argv)
{
	if(argc > 1) {
		FILE *file;
		file=fopen(argv[1], "r");
		if(!file) {
			fprintf(stderr, "no se puede abrir %s\n", argv[1]);
			exit(1);
		}
		yyin = file;
	}//else yyin=stdin;
	yylex();
  if(strncmp(tipoGraph,"g",1)==0){
    printf("El grafo \"%s\" no es dirigido\n",nombreGrafo);
    fflush(stdout);
  }
  if(strncmp(tipoGraph,"d",1)==0){
    printf("El grafo \"%s\" es dirigido\n",nombreGrafo);
    fflush(stdout);
  }
  if(subgrafoLargo==NULL){
    printf("No hay subgrafos\n");
    fflush(stdout);
  }else{
    printf("El subgrafo con el nombre más largo es: \"%s\"\n",subgrafoLargo);
    fflush(stdout);
  }
  printf("El nodo \"%s\" es el que más segmentos tiene: %d\n",nombreNodo,numladosmayor);
  fflush(stdout);
  if(etiquetalarga==NULL){
    printf("No hay nodos con etiqueta\n");
  }else{
    printf("La etiqueta mas larga es: %s\n",etiquetalarga);
    fflush(stdout);
  }
  return 0;
}
