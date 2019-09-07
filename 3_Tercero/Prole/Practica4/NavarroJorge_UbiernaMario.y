/*
@author: Mario Ubierna San Mames y Jorge Navarro Gonzalez
@date: 10/12/2017
@version: 1.0
*/

%{
//includes
#include <stdio.h>
#include<stdlib.h>

//Calcular la etiqueta
int etiqueta()
{
   static int nextNumber=-1;
   return ++nextNumber;
}
//Calcular el número de la variable V
int vnumero()
{
    static int contadorSwitch=-1;
    return ++contadorSwitch;
}
//declaración de variables
extern FILE *yyin;
//int yydebug=1;
int inicial=0; //etiqueta del IF
int despues=0; //etiqueta del ELSE
int vswitch=-1; //etiqueta de la variable V
int etiquetaSwitch=0; //etiqueta del SWITCH
int etiquetaDo=0; //etiqueta del DO
int etiquetaDoW=0; //etiqueta del DO-WHILE
int etiquetaW=0; //etiqueta del WHILE
int etiquetaCase=0; //etiqueta del CASE
%}


%union {
//union ya que yylval puede ser un numero o un string
    int num;
    char *let;
}

//Creación de los tokens correspondientes a los del fichero flex
%token <num>NUM
%token <let>ID
%token IF
%token ELSE
%token FI
%token SWITCH
%token WHILE
%token CASE
%token DO
%token PRINT
%token ASSIG
%token MAIG
%token MEIG

%%
statList: stat
        | stat ';' statList;

stat: '{' statList '}'
    | assig
    | struct
    ;

assig: ID ASSIG { printf("\tvalori %s\n", $<let>1); } exp { printf("\tasigna\n"); }
     | ID MAIG { printf("\tvalori %s\n\tvalord %s\n", $<let>1,$<let>1); } exp { printf("\tsum\n"); } { printf("\tasigna\n"); }
     | ID MEIG { printf("\tvalori %s\n\tvalord %s\n", $<let>1,$<let>1); } exp { printf("\tsub\n"); } { printf("\tasigna\n"); }
     ;
/**
expresiones regulares
<IF> <PARA> exp() <PARC> stat() ((<ELSE> stat()) <FI> | (<FI>))
<SWITCH> <PARA> exp() <PARC> <LLA> statCase() <LLC>
<WHILE> <PARA> exp() <PARC> stat()
<DO> stat() <WHILE> <PARA> exp() <PARC>
<PRINT> <PARA> exp() <PARC>
*/
struct: IF '(' exp expif ')' stat {printf("LBL%d\n",inicial);} FI
      | IF '(' exp expif ')' stat ELSE {despues=etiqueta(); printf("\tvea LBL%d\n",despues);printf("LBL%d\n",inicial);} stat {printf("LBL%d\n",despues);} FI
      | SWITCH {vswitch=vnumero(); printf("\tvalori V%d\n",vswitch);} '(' exp {printf("\tasigna\n");} ')' '{' {etiquetaSwitch=etiqueta();} statCase {printf("LBL %d\n",etiquetaSwitch);} '}'
      | WHILE {etiquetaW=etiqueta(); printf("LBL%d\n",etiquetaW);} '(' exp {despues=etiqueta(); printf("\tsifalsovea LBL%d\n",despues);} ')' stat {printf("\tvea LBL%d\n",etiquetaW); printf("LBL%d\n",despues);}
      | DO {etiquetaDo=etiqueta(); printf("LBL %d\n",etiquetaDo);} stat WHILE '(' exp {etiquetaDoW=etiqueta(); printf("\tsifalsovea LBL%d\n",etiquetaDoW);} ')' {printf("\tvea LBL%d\n",etiquetaDo); printf("LBL %d\n",etiquetaDoW);}
      | PRINT '(' exp ')' {printf("\tprint\n");}
      ;

expif: {inicial=etiqueta(); printf("\tsifalsovea LBL%d\n",inicial);}
     ;

/**
expresion regular
<CASE> exp() <PUN> stat() (statCase())?
*/
statCase: CASE exp expCase
        | CASE exp expCase statCase
        ;

expCase: {printf("\tvalord V%d\n\tsub\n",vswitch);} ':' {etiquetaCase=etiqueta(); printf("\tsiciertovea LBL%d\n",etiquetaCase);} stat {printf("\tvea LBL%d\n",etiquetaSwitch); printf("LBL %d\n",etiquetaCase);}
       ;

exp: exp '+' mexp { printf("\tsum\n"); }
   | exp '-' mexp { printf("\tsub\n"); }
   | mexp
   ;

mexp: mexp '*' value { printf("\tmul\n"); }
    | mexp '/' value { printf("\tdiv\n"); }
    | value
    ;

value: NUM { printf("\tmete %d\n", $<num>1); }
     | ID { printf("\tvalord %s\n", $<let>1); }
     | '(' exp ')'
     ;
%%

yyerror(char *s){printf("%s\n",s);}
int main(int argc, char **argv)
{
	if(argc > 1) {
		FILE *file;
		file=fopen(argv[1], "r");
		if(!file) {
			fprintf(stderr, "no se puede abrir %s\n", argv[1]);
			exit(1);
		}
		yyin=file;
	}//else yyin=stdin;
    yyparse();
    return 0;
}
