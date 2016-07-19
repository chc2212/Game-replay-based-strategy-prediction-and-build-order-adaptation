#include <stdio.h>
#include <string.h> 
#include <stdlib.h>

#define NUM_CLASS 7

 #define PROTOSS 1
// #define TERRAN 1 
//#define ZERG 1 

#ifdef PROTOSS
	#define NUM_ATTR 57 
//	#define NUM_DATA (542+1139+1024)
//	#define NUM_DATA (1140+1139+1024) //chc
	#define NUM_DATA 1140   
#endif 

#ifdef TERRAN 
    #define NUM_ATTR 52
	#define NUM_DATA (1139+628+1150)
#endif

#ifdef ZERG 
	#define NUM_ATTR 49
	#define NUM_DATA (1024+1150+1010)
#endif

  
void remove_comma(char str[200],char attr[NUM_ATTR][1000], char mode[20]){
	FILE *fin=fopen(str,"r"); 
	FILE *fout=fopen("temp.txt",mode); 
	int i; 
	char t1[1000],t2[1000],t3[1000]; 

	fscanf(fin,"%s%s",t1,t2); 
	
	for(i=0;i<NUM_ATTR;i++){
		fscanf(fin,"%s %s %s",t1,attr[i],t3); 
	}

	fscanf(fin,"%s",t1); 
	
    while(1){
		char ch=fgetc(fin); 
		if(ch==-1) break; 
		if(ch==',') fprintf(fout," "); 
		else fprintf(fout,"%c",ch); 

	}
	fclose(fin);  
	fclose(fout); 
}

void perfect_rule(int data[NUM_DATA][NUM_ATTR],char attr[NUM_ATTR][1000],char left_large[NUM_CLASS][NUM_ATTR][NUM_ATTR],int class_index){
	int i,j,k; 
	int i_large; 
	int num_sample_for_the_index=0; 

	for(i=0;i<NUM_DATA;i++){
		if(data[i][NUM_ATTR-1]==class_index) num_sample_for_the_index++; 
	}

	for(i=0;i<NUM_ATTR-1;i++){
		for(j=0;j<NUM_ATTR-1;j++){
			if(i==j) continue; 
			i_large=0;
			for(k=0;k<NUM_DATA;k++){
				if(data[k][NUM_ATTR-1]==class_index){
				   if(data[k][i]>data[k][j]) i_large++; 
				   else break; 
				}
			}
			if(i_large==num_sample_for_the_index){
				left_large[class_index][i][j]=1; 
			}
		
		}
	}

}

void count_for_the_class(char left_large[NUM_CLASS][NUM_ATTR][NUM_ATTR],int count[NUM_CLASS]){
	int i=0,j=0,k=0; 
	for(i=0;i<NUM_CLASS-1;i++) count[i]=0; 

		for(i=0;i<NUM_CLASS-1;i++){
		for(j=0;j<NUM_ATTR-1;j++){
			for(k=0;k<NUM_ATTR-1;k++){
				if(left_large[i][j][k]==1) {
				//	printf("%d %d %d %d %d\n",i,j,k,left_large[i][j][k],count_for_the_class[i]); 
					count[i]++; 
				}
			}
		}
		printf("%d ",count[i]); 
	}
		printf("\n"); 

}

int is_the_same_left_large(int i,int j,char left_large[NUM_CLASS][NUM_ATTR][NUM_ATTR]){
	int k,m;
	int diff=0; 
	for(k=0;k<NUM_ATTR-1;k++){
		for(m=0;m<NUM_ATTR-1;m++){
			if(left_large[i][k][m]!=left_large[j][k][m]) {diff=1; break;}
		}
	}

	return diff; 
}

void reduce_rule(char left_large[NUM_CLASS][NUM_ATTR][NUM_ATTR]){
	// print the number of rules for each class
	int i,j,k,m;
	int count[NUM_CLASS]; 
	int temp; 
	int back_up[NUM_CLASS]; 

   

	// delete rules one by one 
	for(j=0;j<NUM_ATTR-1;j++){
		for(k=0;k<NUM_ATTR-1;k++){
			count_for_the_class(left_large,count); 

			temp=0; 
			for(i=0;i<NUM_CLASS-1;i++) if(left_large[i][j][k]!=0) temp++; 
			if(temp==0) continue; 
			
			for(i=0;i<NUM_CLASS-1;i++) back_up[i]=left_large[i][j][k]; 
			
			for(i=0;i<NUM_CLASS-1;i++) left_large[i][j][k]=0; 
			
			count_for_the_class(left_large,count); 

			// the deletion makes empty rule for the class 
			temp=0; 
			for(i=0;i<NUM_CLASS-1;i++) if(count[i]==0) {temp=1; break;}

			if(temp==1) {
				// back_up 
				for(i=0;i<NUM_CLASS-1;i++) left_large[i][j][k]=back_up[i]; 
				continue; 
			}

			// the deletion makes the same rules for the two classes 
			temp=0; 
			for(i=0;i<NUM_CLASS-1;i++){
				for(m=i+1;m<NUM_CLASS-1;m++){
					if(is_the_same_left_large(i,m,left_large)==0) {temp=1;  break;}
				}
			}

			if(temp==1) {
				// back up 
				for(i=0;i<NUM_CLASS-1;i++) left_large[i][j][k]=back_up[i]; 
				continue; 
			}

		}
	}

}

void print_rule(char attr[NUM_ATTR][1000],char left_large[NUM_CLASS][NUM_ATTR][NUM_ATTR]){
	FILE *fout=fopen("result.txt","a"); 
	int class_index; 
	int i,j; 

	for(class_index=0;class_index<NUM_CLASS-1;class_index++){
	fprintf(fout,"CLASS_INDEX = %d\n",class_index); 

		for(i=0;i<NUM_ATTR-1;i++){
			for(j=0;j<NUM_ATTR-1;j++){
				if(left_large[class_index][i][j]==1)	fprintf(fout,"%s > %s\n",attr[i],attr[j]); 
			}
		}
	}

	fclose(fout); 
}

void print_arff(int data[NUM_DATA][NUM_ATTR], char attr[NUM_ATTR][1000],char left_large[NUM_CLASS][NUM_ATTR][NUM_ATTR]){
	int i=0; 
	int j=0,k=0,c=1; 
	int not_zero_attr_x[NUM_ATTR*NUM_ATTR]={0}; 
	int not_zero_attr_y[NUM_ATTR*NUM_ATTR]={0}; 
	int not_zero_attr_count=0; 

	FILE *c1=fopen("1.arff","w"); 
	FILE *c2=fopen("2.arff","w"); 
	FILE *c3=fopen("3.arff","w");
	FILE *c4=fopen("4.arff","w");
	FILE *c5=fopen("5.arff","w");
	FILE *c6=fopen("6.arff","w");
	FILE *c7=fopen("7.arff","w");
	FILE *c8=fopen("8.arff","w"); 
	FILE *c9=fopen("9.arff","w"); 
	FILE *c10=fopen("10.arff","w");
	FILE *c11=fopen("11.arff","w");
	FILE *c12=fopen("12.arff","w");
	FILE *c13=fopen("13.arff","w");
	FILE *c14=fopen("14.arff","w");
	FILE *c15=fopen("15.arff","w"); 
	FILE *c16=fopen("16.arff","w"); 
	FILE *c17=fopen("17.arff","w");
	FILE *c18=fopen("18.arff","w");
	FILE *c19=fopen("19.arff","w");
	FILE *c20=fopen("20.arff","w");
	FILE *c21=fopen("21.arff","w");
	FILE *c22=fopen("22.arff","w"); 
	FILE *c23=fopen("23.arff","w"); 
	FILE *c24=fopen("24.arff","w");
	FILE *c25=fopen("25.arff","w");
	FILE *c26=fopen("26.arff","w");
	FILE *c27=fopen("27.arff","w");
	FILE *c28=fopen("28.arff","w");
	FILE *c29=fopen("29.arff","w"); 
	FILE *c30=fopen("30.arff","w"); 
	FILE *c31=fopen("31.arff","w");


	fprintf(c1,"@RELATION Starcraft\n\n");
	fprintf(c2,"@RELATION Starcraft\n\n");
	fprintf(c3,"@RELATION Starcraft\n\n");
	fprintf(c4,"@RELATION Starcraft\n\n");
	fprintf(c5,"@RELATION Starcraft\n\n");
	fprintf(c6,"@RELATION Starcraft\n\n");
	fprintf(c7,"@RELATION Starcraft\n\n");
	fprintf(c8,"@RELATION Starcraft\n\n");
	fprintf(c9,"@RELATION Starcraft\n\n");
	fprintf(c10,"@RELATION Starcraft\n\n");
	fprintf(c11,"@RELATION Starcraft\n\n");
	fprintf(c12,"@RELATION Starcraft\n\n");
	fprintf(c13,"@RELATION Starcraft\n\n");
	fprintf(c14,"@RELATION Starcraft\n\n");
	fprintf(c15,"@RELATION Starcraft\n\n");
	fprintf(c16,"@RELATION Starcraft\n\n");
	fprintf(c17,"@RELATION Starcraft\n\n");
	fprintf(c18,"@RELATION Starcraft\n\n");
	fprintf(c19,"@RELATION Starcraft\n\n");
	fprintf(c20,"@RELATION Starcraft\n\n");
	fprintf(c21,"@RELATION Starcraft\n\n");
	fprintf(c22,"@RELATION Starcraft\n\n");
	fprintf(c23,"@RELATION Starcraft\n\n");
	fprintf(c24,"@RELATION Starcraft\n\n");
	fprintf(c25,"@RELATION Starcraft\n\n");
	fprintf(c26,"@RELATION Starcraft\n\n");
	fprintf(c27,"@RELATION Starcraft\n\n");
	fprintf(c28,"@RELATION Starcraft\n\n");
	fprintf(c29,"@RELATION Starcraft\n\n");
	fprintf(c30,"@RELATION Starcraft\n\n");
	fprintf(c31,"@RELATION Starcraft\n\n");





	for(j=0;j<NUM_ATTR-1;j++){
		for(k=0;k<NUM_ATTR-1;k++){
			int not_zero=0; 
			for(i=0;i<NUM_CLASS-1;i++){
				if(left_large[i][j][k]!=0) {not_zero=1;break;}
			}
			if(not_zero==1) {
				not_zero_attr_x[not_zero_attr_count]=j;
				not_zero_attr_y[not_zero_attr_count]=k; 
				not_zero_attr_count++; 
			
				fprintf(c1,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c2,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c3,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c4,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c5,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c6,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c7,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c8,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c9,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c10,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c11,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c12,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c13,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c14,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c15,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c16,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c17,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c18,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c19,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c20,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c21,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c22,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c23,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c24,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c25,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c26,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c27,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c28,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c29,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c30,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);
				fprintf(c31,"@ATTRIBUTE %s_%s INTEGER\n",attr[j],attr[k]);			
				
			}
		}
	}
  
	
  
		fprintf(c1,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c2,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c3,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c4,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c5,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c6,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c7,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c8,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c9,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c10,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c11,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c12,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c13,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c14,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c15,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c16,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c17,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c18,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c19,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c20,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c21,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c22,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c23,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c24,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c25,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c26,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c27,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c28,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c29,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c30,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
		fprintf(c31,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n@DATA\n");
	
	/*

			fprintf(center_word,"@ATTRIBUTE class {0,1,2,3,4,5,6}\n\n"); 
			fprintf(center_word,"@DATA\n"); 
*/

	for(i=0;i<NUM_DATA;i++){
		for(j=0;j<not_zero_attr_count;j++){
		//	if(data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(fout,"1,");  

		
			c=1;
		
			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c1,"1,"); //chc수정
			else fprintf(c1,"0,");
			c++;
			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c2,"1,"); //chc수정
			else fprintf(c2,"0,");
			c++;
	
			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c3,"1,"); //chc수정
			else fprintf(c3,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c4,"1,"); //chc수정
			else fprintf(c4,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c5,"1,"); //chc수정
			else fprintf(c5,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c6,"1,"); //chc수정
			else fprintf(c6,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c7,"1,"); //chc수정
			else fprintf(c7,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c8,"1,"); //chc수정
			else fprintf(c8,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c9,"1,"); //chc수정
			else fprintf(c9,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c10,"1,"); //chc수정
			else fprintf(c10,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c11,"1,"); //chc수정
			else fprintf(c11,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c12,"1,"); //chc수정
			else fprintf(c12,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c13,"1,"); //chc수정
			else fprintf(c13,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c14,"1,"); //chc수정
			else fprintf(c14,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c15,"1,"); //chc수정
			else fprintf(c15,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c16,"1,"); //chc수정
			else fprintf(c16,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c17,"1,"); //chc수정
			else fprintf(c17,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c18,"1,"); //chc수정
			else fprintf(c18,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c19,"1,"); //chc수정
			else fprintf(c19,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c20,"1,"); //chc수정
			else fprintf(c20,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c21,"1,"); //chc수정
			else fprintf(c21,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c22,"1,"); //chc수정
			else fprintf(c22,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c23,"1,"); //chc수정
			else fprintf(c23,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c24,"1,"); //chc수정
			else fprintf(c24,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c25,"1,"); //chc수정
			else fprintf(c25,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c26,"1,"); //chc수정
			else fprintf(c26,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c27,"1,"); //chc수정
			else fprintf(c27,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c28,"1,"); //chc수정
			else fprintf(c28,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c29,"1,"); //chc수정
			else fprintf(c29,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c30,"1,"); //chc수정
			else fprintf(c30,"0,");
			c++;

			if((data[i][not_zero_attr_x[j]] <= (c-1)*500  && data[i][not_zero_attr_y[j]] <= (c-1)*500) && data[i][not_zero_attr_x[j]] > data[i][not_zero_attr_y[j]]) fprintf(c31,"1,"); //chc수정
			else fprintf(c31,"0,");
			c++;

			

		}
	
			fprintf(c1,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c2,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c3,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c4,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c5,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c6,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c7,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c8,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c9,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c10,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c11,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c12,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c13,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c14,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c15,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c16,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c17,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c18,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c19,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c20,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c21,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c22,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c23,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c24,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c25,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c26,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c27,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c28,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c29,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c30,"%d\n",data[i][NUM_ATTR-1]);
			fprintf(c31,"%d\n",data[i][NUM_ATTR-1]);
	
	
	}


		fclose(c1);
		fclose(c2);
		fclose(c3);
		fclose(c4);
		fclose(c5);
		fclose(c6);
		fclose(c7);
		fclose(c8);
		fclose(c9);
		fclose(c10);
		fclose(c11);
		fclose(c12);
		fclose(c13);
		fclose(c14);
		fclose(c15);
		fclose(c16);
		fclose(c17);
		fclose(c18);
		fclose(c19);
		fclose(c20);
		fclose(c21);
		fclose(c22);
		fclose(c23);
		fclose(c24);
		fclose(c25);
		fclose(c26);
		fclose(c27);
		fclose(c28);
		fclose(c29);
		fclose(c30);
		fclose(c31);


}

void  main(void){

	FILE *fin,*fout; 
    char str[1000]; 
	int i,j; 

	char attr[NUM_ATTR][1000]; 
	int data[NUM_DATA][NUM_ATTR]; 
	char left_large[NUM_CLASS][NUM_ATTR][NUM_ATTR]={0}; 
	int max; 
	

#ifdef PROTOSS 
	remove_comma(".\\traces\\scmPvP_Protoss_Mid.arff",attr,"w"); // 542 
	remove_comma(".\\traces\\scmPvT_Protoss_Mid.arff",attr,"a"); // 1139
	remove_comma(".\\traces\\scmPvZ_Protoss_Mid.arff",attr,"a"); // 1024
#endif 

#ifdef TERRAN 
	remove_comma(".\\traces\\scmPvT_Terran_Mid.arff",attr,"w");  
	remove_comma(".\\traces\\scmTvT_Terran_Mid.arff",attr,"a"); 
	remove_comma(".\\traces\\scmTvZ_Terran_Mid.arff",attr,"a"); 
#endif

#ifdef ZERG 
	remove_comma(".\\traces\\scmPvZ_Zerg_Mid.arff",attr,"w");  
	remove_comma(".\\traces\\scmTvZ_Zerg_Mid.arff",attr,"a"); 
	remove_comma(".\\traces\\scmZvZ_Zerg_Mid.arff",attr,"a"); 
#endif

 
	
	fin=fopen("temp.txt","r"); 

	for(i=0;i<NUM_DATA;i++){
		for(j=0;j<NUM_ATTR-1;j++){
			fscanf(fin,"%d",&data[i][j]); 
		} 
		fscanf(fin,"%s",str); 
 
#ifdef PROTOSS
		if(strcmp(str,"FastLegs")==0) data[i][NUM_ATTR-1]=0; 
		else if(strcmp(str,"FastDT")==0) data[i][NUM_ATTR-1]=1; 
		else if(strcmp(str,"FastObs")==0) data[i][NUM_ATTR-1]=2; 
		else if(strcmp(str,"ReaverDrop")==0) data[i][NUM_ATTR-1]=3; 
		else if(strcmp(str,"Carrier")==0) data[i][NUM_ATTR-1]=4; 
		else if(strcmp(str,"FastExpand")==0) data[i][NUM_ATTR-1]=5; 
		else if(strcmp(str,"Unknown")==0) data[i][NUM_ATTR-1]=6; 
		else data[i][NUM_ATTR-1]=-1; // error 
#endif 

#ifdef TERRAN
		if(strcmp(str,"Bio")==0) data[i][NUM_ATTR-1]=0; 
		else if(strcmp(str,"TwoFactory")==0) data[i][NUM_ATTR-1]=1; 
		else if(strcmp(str,"VultureHarass")==0) data[i][NUM_ATTR-1]=2; 
		else if(strcmp(str,"SiegeExpand")==0) data[i][NUM_ATTR-1]=3; 
		else if(strcmp(str,"Standard")==0) data[i][NUM_ATTR-1]=4; 
		else if(strcmp(str,"FastDropship")==0) data[i][NUM_ATTR-1]=5; 
		else if(strcmp(str,"Unknown")==0) data[i][NUM_ATTR-1]=6; 
		else data[i][NUM_ATTR-1]=-1; // error 
#endif 

#ifdef ZERG
		if(strcmp(str,"TwoHatchMuta")==0) data[i][NUM_ATTR-1]=0; 
		else if(strcmp(str,"ThreeHatchMuta")==0) data[i][NUM_ATTR-1]=1; 
		else if(strcmp(str,"HydraRush")==0) data[i][NUM_ATTR-1]=2; 
		else if(strcmp(str,"Standard")==0) data[i][NUM_ATTR-1]=3; 
		else if(strcmp(str,"HydraMass")==0) data[i][NUM_ATTR-1]=4; 
		else if(strcmp(str,"Lurker")==0) data[i][NUM_ATTR-1]=5; 
		else if(strcmp(str,"Unknown")==0) data[i][NUM_ATTR-1]=6; 
		else data[i][NUM_ATTR-1]=-1; // error 
#endif 

		if(data[i][NUM_ATTR-1]==-1) printf("Error!!! str=%s\n",str); 
	}

	// find the max value for the data 
	max=0; 
	for(i=0;i<NUM_DATA;i++){
		for(j=0;j<NUM_ATTR-1;j++){
			if(max<data[i][j]) max=data[i][j]; 
		}
	}

	for(i=0;i<NUM_DATA;i++){
		for(j=0;j<NUM_ATTR-1;j++){
			if(data[i][j]==0) data[i][j]=max+1;  // not builded 
		}
	}


    // find the rule perfectly classify the class 

	fout=fopen("result.txt","w"); fclose(fout); 

	// Except unknown class 

	for(i=0;i<NUM_CLASS-1;i++){
		perfect_rule(data,attr,left_large,i); 
	}

	print_arff(data,attr,left_large);

	// reduce number of rules 
	reduce_rule(left_large); 

	print_rule(attr,left_large); 

	fclose(fin); 
       
}
