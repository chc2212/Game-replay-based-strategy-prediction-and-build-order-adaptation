#include <stdio.h>
#include <string.h> 

#define NUM_CLASS 7

#define PROTOSS 1
// #define TERRAN 1 
// #define ZERG 1 

#ifdef PROTOSS
	#define NUM_ATTR 57 
	#define NUM_DATA (542+1139+1024)
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


void  main(void){

	FILE *fin; 
    char str[1000]; 
	int i,j; 

	char attr[NUM_ATTR][1000]; 
	int data[NUM_DATA][NUM_ATTR]; 
	char left_large[NUM_CLASS][NUM_ATTR][NUM_ATTR]={0}; 

	int Citadel; 
	int RoboticsBay; 
	int ZealotLegs; 
	int TemplarArchives; 
	int StarGate;
	int Nexus; 
	int SupportBay; 
	int Observatory; 
	int strategy; 
	int correct; 
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

	printf("max=%d\n",max); getchar(); 

    // what is the first building produced among them? 
	// zealot legs, archives, stargate, nexus, support Bay, Observatory 
	// citadel 24
	// robotics_bay 33
    // zealot legs 25 
	// archives 26
	// stargate 42 
	// nexus 4
	// support bay 36 
	// Observatory 40 

	correct=0; 

	for(i=0;i<NUM_DATA;i++){
		Citadel=data[i][24]; 
		RoboticsBay=data[i][33]; 
		ZealotLegs=data[i][25]; 
		TemplarArchives=data[i][26]; 
		StarGate=data[i][42];
		Nexus=data[i][4]; 
		SupportBay=data[i][36]; 
		Observatory=data[i][40]; 
		strategy; 

		if(Citadel == 0) Citadel = max+1; 
		if(RoboticsBay == 0) RoboticsBay = max+1; 
		if(ZealotLegs == 0) ZealotLegs = max+1; 
		if(TemplarArchives == 0) TemplarArchives = max+1; 
		if(StarGate == 0) StarGate = max+1; 
		if(Nexus == 0) Nexus = max+1; 
		if(SupportBay == 0) SupportBay = max+1; 
		if(Observatory == 0) Observatory = max+1;
		

		if (Citadel < StarGate && Citadel < Nexus && Citadel < RoboticsBay) {
			 if (ZealotLegs <TemplarArchives) { strategy=0; } // then "Fast Legs" 
		     else if (TemplarArchives < ZealotLegs) { strategy=1; }  // then "Fast DT"
			 else {
				strategy=6;	// then "unknown"
			}
		}
		else if (StarGate<Citadel && StarGate < Nexus && StarGate < RoboticsBay) {
			    strategy=4; // then "Fast Air"
		}
        else if (Nexus< Citadel && Nexus < StarGate && Nexus < RoboticsBay) {
				strategy=5;	//then "Fast Expand"
		}
        else if (RoboticsBay < Citadel && RoboticsBay < StarGate && RoboticsBay < Nexus) {
			if (SupportBay < Observatory) {
				strategy=3;	// then "Reaver"
			}
			else if (Observatory < SupportBay) {
				strategy=2; // then "Standard"
			}
			else {
				strategy=6; //then "unknown"
			}
		}
		else {
			strategy=6; 
		}


		if(data[i][NUM_ATTR-1]==strategy) {correct++; } // printf("OK"); 
		else {
			/* 
			printf("NOT OK\n"); 
			printf("Citadel = %d StarGate = %d Nexus = %d RoboticsBay = %d\n",Citadel,StarGate, Nexus, RoboticsBay); 
			printf("SupportBay = %d Observatory = %d \n",SupportBay, Observatory); 
			
			printf("%d %d %d\n",i,data[i][NUM_ATTR-1],strategy); getchar(); 
			*/ 
		
		}


	}

	printf("correct=%d accuracy = %f\n",correct,(double)correct/NUM_DATA); 


	fclose(fin); 
       
}
