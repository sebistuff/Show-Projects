#ifndef CAMPAIGN_H
#define CAMPAIGN_H
//#include "hero.h"
//#include "monster.h"
class Player;
class Hero;
class Monster;
#include <memory>
#include <algorithm>
#include <iostream>
#include <vector>
#include <string>
#include <map>
using namespace std;

class Campaign{

private:
	string name;
	map<unsigned, weak_ptr<Hero>> heroes;
	unsigned min_level;

public: 
	Campaign(string name, unsigned min_level):name{name},min_level{min_level}{
		if(name.size()==0) throw runtime_error("Campaign Constructor - name empty");
		if(min_level<=0) throw runtime_error("Campaign Constructor - min_level under 1");
		
	}
	void add_hero(shared_ptr<Hero> h){
		if(!h)throw runtime_error("Campaign - nullptr hero");
		
		auto h_id = h->get_id();
		if(heroes.count(h_id))throw runtime_error("Campaign - hero already member");
		
		if((h->get_level()) < min_level) throw runtime_error("Campaign - lvl too low");
				
		heroes[h_id]=h;
	}
	
	void encounter_monster(unsigned id, Monster& m){
		auto hero_ptr=heroes[id].lock();
		if(!hero_ptr)throw runtime_error("Campaign encounter_monster() - hero not in list");
		
		hero_ptr->fight(m);
		if(m.is_dead()) hero_ptr->level_up();
			
	}
	
	unsigned remove_dead_and_expired(){
		 unsigned count = 0;
		for (auto it = heroes.begin(); it != heroes.end(); ) {
			if (it->second.expired() || it->second.lock()->get_current_hp() == 0) {
				++count;
            		it = heroes.erase(it); 
        		}
        		else ++it;
    		}
    		return count;
	}
	
	inline ostream& print(ostream &o)const{
	// [name Campaign, Min_Level min_level, {heroes0, ..., heroesn}]
	o<<"["<<name<<" Campaign, Min_Level "<<min_level<<", {";
	bool first = true;
     for (const auto& pair : heroes) {
		auto hero_ptr = pair.second.lock();  // Versuche, den Weak Pointer zu sperren
		if (hero_ptr) {
			if (!first) {
			o << ", ";
			}
            first = false;
            o << *hero_ptr;  // Hero-Objekt ausgeben (setzt voraus, dass Hero einen passenden `<<`-Operator hat)
		}
	}

		o << "}]";  // Schließe die Liste ab
		return o;
	}
};

inline ostream& operator<<(ostream &o, const Campaign &c){return c.print(o);}

#endif
