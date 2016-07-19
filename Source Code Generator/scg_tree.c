#include <stdio.h>
#include <string.h> 


#define MAX_INDENT 10

void  main(void){


      char filename[]="protoss_tree.txt"; 
	  FILE *fin=fopen(filename,"r"); 
	  FILE *fout=fopen("temp.txt","w"); 
	  char str[1000],str2[1000],str3[1000],str4[100]; 
	  int indentation_count=0; 
	  int i=0,j=0; 
	  int if_or_else[MAX_INDENT]={0}; 
	  int must_close_block[MAX_INDENT]={0}; 

	  if(filename[0]>='a' && filename[0]<='z') filename[0]-=32; 

	  while(1){
		int check=fscanf(fin,"%s",str); 
		if(check==-1) break; 
		else if(strcmp(str,"|")==0) indentation_count++; 
		else if(str[0]==filename[0]) {
			fprintf(fout,"\n%d ",indentation_count); 
			indentation_count=0;
			fscanf(fin,"%s %s",str2,str3); 
			for(i=0;i<(signed)strlen(str);i++){
				if(str[i]=='_') fprintf(fout," %s ",str2); 
				else fprintf(fout,"%c",str[i]); 
			}
			if(str3[strlen(str3)-1]==':') {
				fscanf(fin,"%s",str4); 
				fprintf(fout," classValue=%s;",str4); 
			}
			else {
				fprintf(fout," nothing"); 
			}
			
		} 
		else if(str[0]=='(') {} 
		else fprintf(fout," %s ",str); 
	  }

	  fclose(fin); 
	  fclose(fout); 

	 
	  fin=fopen("temp.txt","r"); 
	  fout=fopen("output.txt","w"); 
	  
	  while(1){
		int n=fscanf(fin,"%d",&indentation_count); 

		if(n==-1) indentation_count=0;  

		// close block 
		for(i=MAX_INDENT-1;i>=indentation_count;i--) {
			if(must_close_block[i]==1) {
				for(j=0;j<i;j++) fprintf(fout,"\t"); 
				fprintf(fout,"}\n"); 
				must_close_block[i]=0; 
			}
		}

		if(n==-1) break; 
		

		for(i=0;i<indentation_count;i++) fprintf(fout,"\t"); 
		
		fscanf(fin,"%s%s%s%s",str,str2,str3,str4); 
		
		if(if_or_else[indentation_count]==0) {
			fprintf(fout,"if(%s %s %s){\n",str,str2,str3); 
			if_or_else[indentation_count]=1; 
		}
		else {
			fprintf(fout,"else{\n");
			if_or_else[indentation_count]=0; 
		}

		if(strcmp(str4,"nothing")==0) {}
		else {
			for(i=0;i<indentation_count+1;i++) fprintf(fout,"\t"); 
			fprintf(fout,"%s\n",str4); 
		}
		
		must_close_block[indentation_count]=1; 
		
	  }

	  fclose(fin); 
	  fclose(fout); 
       
}
