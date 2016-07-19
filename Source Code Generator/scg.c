#include <stdio.h>
#include <string.h> 


void  main(void){


      FILE *fin=fopen("..\\scmPvP_Protoss_Mid.arff","r"); 
	//  FILE *fin=fopen("..\\scmPvT_Terran_Mid.arff","r"); 
	//  FILE *fin=fopen("..\\scmTvZ_Zerg_Mid.arff","r"); 
	  FILE *fout=fopen("output.txt","w"); 
	  int i=0; 

	  char str[200],str2[200],str3[800]; 
	  fscanf(fin,"%s%s",str,str2); 
	  while(1){
		  fscanf(fin,"%s%s%s",str,str2,str3);
		  if(strcmp(str2,"midBuild")==0) break;
		  fprintf(fout,"int %s;\n",str2); 
		  fprintf(fout,"if((int)instance.value(%d)==0) %s=max+1; else %s=(int)instance.value(%d);\n",i,str2,str2,i); 
		  i++; 
	  }
	  fclose(fin); 
	  fclose(fout); 
       
}
