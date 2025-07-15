#ifndef MONSTER_H
#define MONSTER_H
//#include "hero.h"
//#include "campaign.h"
class Hero;
class Campaign;
#include <memory>
#include <algorithm>
#include <iostream>
#include <vector>
#include <string>
#include <map>
using namespace std;

class Monster{
private: 
	string name;
	unsigned health;
	unsigned attack;

public: 
	Monster(string name, unsigned health, unsigned attack):name{name}, health{health}, attack{attack}{
		if(name.empty())throw runtime_error(" Monster constructor - name emtpy ");
		if(health<=0)throw runtime_error(" Monster constructor - health zero ");
		if(attack<=0)throw runtime_error(" Monster constructor - attack zero ");
	}
	
	virtual ~Monster(){
	}
	unsigned get_health()const{return health;}
	unsigned get_attack()const{return attack;}

	virtual unsigned calculate_damage(unsigned dmg)const=0;
	
	void take_damage(unsigned dmg){//check ich noch nicht
		unsigned act_dam= calculate_damage(dmg);
		health=(health>act_dam)?health-act_dam :0;
	}
	
	bool is_dead ()const{
		if(health==0)return true;
		return false;
	}
	
virtual string additional_information()const=0;
	
	inline ostream& print(ostream &o)const{
		return o<<"["<<name<<", "<<health<<" HP, "<<attack<<" ATK"<<additional_information()<<"]";
	}
	
};

inline ostream& operator<<( ostream &o, const Monster &m){return m.print(o);}

class Elite_Monster :public Monster{
private:
	unsigned defense;
	
public: 
	Elite_Monster( string name, unsigned health, unsigned attack, unsigned defense):Monster(name, health, attack), defense{defense}{
		if(defense<=0)throw runtime_error(" Elite_Monster constructor - defense zero ");
	}
	
	unsigned calculate_damage(unsigned dmg) const override{
 		return std::max(dmg - defense, 0u);	
  }
	
	string additional_information() const override{return ", "+to_string(defense)+" DEF";}


};

class Standard_Monster: public Monster{
public:
	Standard_Monster(string name, unsigned health, unsigned attack): Monster(name, health,attack){}

	unsigned calculate_damage(unsigned dmg) const override{
		return dmg;
	}

	string additional_information() const override{return "";}
 
};


#endif
