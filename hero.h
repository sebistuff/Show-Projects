#ifndef HERO_H
#define HERO_H
#include "monster.h"
//#include "campaign.h"
//class Monster;
class Campaign;
//class Monster;
#include <memory>
#include <algorithm>
#include <iostream>
#include <vector>
#include <string>
#include <map>
using namespace std;

 enum class Hero_Class{ BARBARIAN, BARD, CLERIC, DRUID, FIGHTER, MONK, PALADIN,
 RANGER, ROGUE, SORCERER, WARLOCK, WIZARD};
 
 enum class Hero_Species{ DRAGONBORN, DWARF, ELF, GNOME, AASIMAR, HALFLING,
 ORC, GOLIATH, HUMAN, TIEFLING};
 
 inline ostream& operator<<(ostream& o, const Hero_Class &m){
  	switch (m){
  	   case Hero_Class::BARBARIAN: return o << "Barbarian";
        case Hero_Class::BARD: return o << "Bard";
        case Hero_Class::CLERIC: return o << "Cleric";
        case Hero_Class::DRUID: return o << "Druid";
        case Hero_Class::FIGHTER: return o << "Fighter";
        case Hero_Class::MONK: return o << "Monk";
        case Hero_Class::PALADIN: return o << "Paladin";
        case Hero_Class::RANGER: return o << "Ranger";
        case Hero_Class::ROGUE: return o << "Rogue";
        case Hero_Class::SORCERER: return o << "Sorcerer";
        case Hero_Class::WARLOCK: return o << "Warlock";
        case Hero_Class::WIZARD: return o << "Wizard";
        default: throw runtime_error( "enum class Hero_class << op: false Hero Class");
    }
 }	
 
  inline ostream& operator<<(ostream& o, const Hero_Species &m) {
    switch (m) {
        case Hero_Species::DRAGONBORN: return o << "Dragonborn";
        case Hero_Species::DWARF: return o << "Dwarf";
        case Hero_Species::ELF: return o << "Elf";
        case Hero_Species::GNOME: return o << "Gnome";
        case Hero_Species::AASIMAR: return o << "Aasimar";
        case Hero_Species::HALFLING: return o << "Halfling";
        case Hero_Species::ORC: return o << "Orc";
        case Hero_Species::GOLIATH: return o << "Goliath";
        case Hero_Species::HUMAN: return o << "Human";
        case Hero_Species::TIEFLING: return o << "Tiefling";
        default: throw runtime_error( "enum class Hero_species << op: false Hero species");
    }
 }
 
 static const vector<string> keys{"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
 
 
 
 class Hero{
 
 private:
 	unsigned id;
 	string name;
 	Hero_Class hero_class;
 	Hero_Species hero_species;
 	unsigned level=1;
 	unsigned max_hp;
 	unsigned current_hp;
 	map<string, unsigned> abilities;
 static inline unsigned next_id; 
 
 public:
	Hero(string name, Hero_Class hero_class, Hero_Species hero_species, unsigned max_hp,const map<string, unsigned>& abilities)
	:id{}, name{name}, hero_class{hero_class}, hero_species{hero_species},max_hp{max_hp},current_hp{max_hp},abilities{abilities}{
 		if(name.size()==0) throw runtime_error(" Hero constructor - name empty");
 		if(level<=0 ||level>=21) throw runtime_error(" Hero constructor - level false");
 		if(max_hp <=0) throw runtime_error (" Hero constructor - max_hp false");
	
 		for_each(abilities.begin(), abilities.end(),[&](const auto& pair){
 			
 			if(find(keys.begin(),keys.end(),pair.first)==keys.end())throw runtime_error(" Hero constructor - ability pair.first false ");
 			if(pair.second<1 ||pair.second>20) throw runtime_error("Hero constructor - ability pair.second false: ");
 		});
 		if(abilities.size()!=keys.size())throw runtime_error(" Hero constructor - ability wrong size ");
    id=next_id++;
 	}
 	
	unsigned get_id()const{return id;}
 	unsigned get_level()const{return level;}
 	unsigned get_current_hp()const{return current_hp;}
 	
 
 	unsigned level_up(){
 		if(level<20)return ++level;
 		return level;
 	}
 	
 	bool fight(Monster& m){
		while (current_hp > 0 && !m.is_dead()) {
			unsigned highest_ability=0;
			for(const auto&[key,value]:abilities){
				if(value>highest_ability)highest_ability=value;
			}
			unsigned damage= (level*highest_ability);
			
			m.take_damage(damage); 
			if (!m.is_dead()) {
            unsigned monster_attack = m.get_attack();
            current_hp = (current_hp > monster_attack) ? current_hp - monster_attack : 0;
			}
    }
		return current_hp>0;
  }
   
 inline	ostream& print(ostream &o)const{
 		// [id,name, (hero_class,hero_species,level),{abilities.charisma,abilities.constitution,abilities.dexterity, ...}, (current_hp/max_hp)HP]
		o<<"["<<id<<", "<<name<<", ("<<hero_class<<", "<<hero_species<<", "<<level<<"), {";
		
		o<< abilities.at("Charisma") << ", " << abilities.at("Constitution") << ", " << abilities.at("Dexterity") << ", " << abilities.at("Intelligence") << ", "<< abilities.at("Strength") << ", " << abilities.at("Wisdom")<<"},";
		
		return o<<" ("<<current_hp<<"/"<<max_hp<<")"<< " HP]";
		
 	}

 };
   
	
	  
   inline ostream& operator<<(ostream &o,const Hero &h){return h.print(o);};	
 #endif
