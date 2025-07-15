#ifndef PLAYER_H
#define PLAYER_H
#include "hero.h"
#include "monster.h"
#include"campaign.h"
#include <memory>
#include <algorithm>
#include <iostream>
#include <vector>
#include <string>
#include <map>
using namespace std;

class Player{
private:
	string first_name;
	string last_name;
	map<unsigned, shared_ptr<Hero>> heroes;
	unique_ptr<Campaign> campaign;

public: 
	 Player(string first_name,string last_name):first_name{first_name},last_name{last_name}, heroes{}, campaign{nullptr}{
	 	if(first_name.empty() || last_name.empty())throw runtime_error("player constructor - name emtpy");
	 }
	 
	 unsigned create_hero(const string& hero_name,Hero_Class hero_class,Hero_Species hero_species,unsigned max_hp,const map<string ,unsigned>&h){
	 	auto new_hero=make_shared<Hero>(hero_name, hero_class, hero_species, max_hp,h);
	 	heroes[new_hero->get_id()]=new_hero;
	 	
	 	return new_hero->get_id();
	 }
	
	void create_campaign(string name,unsigned min_level,unsigned id){
		
		auto new_camp=make_unique<Campaign>(name,min_level);
		auto found = heroes.find(id);
		
		if(found!=heroes.end() )new_camp->add_hero(found->second);
		else throw runtime_error("Player create_campaign() - no hero object to add");		
		
		campaign=move(new_camp);
	}

	void join_friends_campaign(Player& pfriend,unsigned id){
		if(!pfriend.campaign)throw runtime_error("Player join_friends_campaign() - pfriend has no campaign");
		
		auto found=heroes.find(id);
		if(found==heroes.end() )throw runtime_error("Player join_friends_campaign() - this object has no hero");
		pfriend.campaign->add_hero(found->second);
	}

	void transfer_campaign(Player& pfriend){
	 	if(!campaign) throw runtime_error("Player transfer_campaign() - this object has no campaign");
	 	if(pfriend.campaign)pfriend.campaign.reset();
	 	 pfriend.campaign=move(campaign);
	}	 
	unsigned remove_dead(){
		unsigned count{0};
		for(auto it=heroes.begin();it!=heroes.end();){
			weak_ptr<Hero> weak_hero = it->second;
			if (auto hero = weak_hero.lock()) {
				if (hero->get_current_hp() == 0) {
					++count;
					it=heroes.erase(it);
				}
				else ++it;
			}
			else ++it;
		}
		return count;
	}
	ostream& print_campaign(ostream &o)const{
		if(!campaign)return o<<"[]";
		return o<<*campaign;
	}
	inline	ostream& print(ostream &o)const{
	 //[first_namelast_name,{heroes0, ...,heroesn},print_campaign()
		 o<<"["<<first_name<<" "<<last_name<<", {";
	 
		bool first = true;  
		for (const auto& hero_pair : heroes) {
			if (!first) {
            o << ", ";  
			}
			first = false;
			o << *hero_pair.second; 
		}
    o<<"}, ";
	 	
	 	print_campaign(o);
	 
	 	return o<<"]";
	}
};

 inline ostream&operator<<(ostream &o,const Player&p){return p.print(o);}
#endif
