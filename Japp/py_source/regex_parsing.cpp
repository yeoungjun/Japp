#include <iostream>
#include <fstream>
#include <string>
#include <regex>
using namespace std;

int main(){

	ifstream inf("Q_1.txt");
	ofstream outf("R_2.csv");
	string str;
	string str_ip;
	regex r_ip("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
	regex r_date("(\\d{1,4}).(\\d{1,2}).(\\d{1,2}).(\\d{1,2}):(\\d{1,2}):(\\d{1,2})\\s(\\S+):(.+)");
	smatch match;

	do	{
		getline(inf, str);
		if ( regex_search(str, match, r_ip))
			str_ip = match[0];		
		else
			str_ip = "not found";


		if ( regex_search(str, match, r_date)){
			outf<<match[7]<<",";
			outf<<str_ip<<",";
			outf<<match[1]<<"�� " << match[2]<<"�� "<<match[3]<<"�� "<<match[4]<<"�� "<<match[5]<<"�� " << match[6]<<"�� ";
			outf<<","<<match[8];
		}
		outf<<endl;
	}while(!inf.eof());

	outf.close();
	inf.close();
	return 0;
}