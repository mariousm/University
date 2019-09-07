 /*
 @author: Mario Ubierna San Mames y Jorge Navarro Gonzalez
 @date: 10/12/2017
 @version: 1.0
 */

%{
#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include "y.tab.h"
#define CP(FROM,TO) TO=strdup(FROM)
%}

 //estos son todos los estados que hemos creado
%x  COM

%%
"/*"                        {BEGIN(COM);}
<COM>"*/"                   {BEGIN(INITIAL);}
<COM>.|\n                   ;
"#".*\n
"//".*\n
(" "|"\n"|"\t"|"\r")        ;

"if"                        {
                                return IF;
                            }
"else"                      {
                                return ELSE;
                            }
"fi"                        {
                                return FI;
                            }
"switch"                    {
                                return SWITCH;
                            }
"while"                     {
                                return WHILE;
                            }
"case"                      {
                                return CASE;
                            }
"do"                        {
                                return DO;
                            }
"print"                     {
                                return PRINT;
                            }
"<-"                        {
                                return ASSIG;
                            }
"+="                        {
                                return MAIG;
                            }
"-="                        {
                                return MEIG;
                            }

([a-zA-Z])([a-zA-Z0-9]*)    {
                                CP(yytext,yylval.let);
                                return ID;
                            }
                            
[0-9]+	                    {                    
                                yylval.num=atoi(yytext); 
                                return NUM; 
                            }
.	                        return yytext[0];
%%
