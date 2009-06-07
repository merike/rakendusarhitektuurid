package ee.ttu.t061879.EPOOD2.validate;

import ee.ttu.t061879.EPOOD2.utils.Utils;

public class ProductSearchValidator {
	public void validate(ProductSearch s){
		if(s.getName().length() > 100){
			s.nameOk = false;
			s.nameError = "liiga pikk";
		}
		else s.nameOk = true;
		
		if(s.getDesc().length() > 300){
			s.descOk = false;
			s.descError = "liiga pikk";
		}
		else s.descOk = true;
		
		try{
			Utils.number(s.getMaxPrice());
			s.maxPriceOk = true;
		}
		catch (NumberFormatException e) {
			if(s.getMaxPrice().length() == 0) s.maxPriceOk = true; 
			else{
				s.maxPriceOk = false;
				s.maxPriceError = "ei ole korrektne hind";
			}
		}
		
		try{
			Utils.number(s.getMinPrice());
			s.minPriceOk = true;
		}
		catch (NumberFormatException e) {
			if(s.getMinPrice().length() == 0) s.minPriceOk = true;
			else{
				s.minPriceOk = false;
				s.minPriceError = "ei ole korrektne hind";
			}
		}
		
		if(s.getAddedBy().length() > 101){
			s.addedByOk = false;
			s.addedByError = "liiga pikk";
		}
		else s.addedByOk = true;
		
		if(s.getChangedBy().length() > 101){
			s.changedByOk = false;
			s.changedByError = "liiga pikk";
		}
		else s.changedByOk = true;
		
		try{
			Utils.isoDate(s.getMinAdded());
			s.minAddedOk = true;
		}
		catch (Exception e) {
			if(s.minAdded.length() == 0) s.minAddedOk = true;
			else{
				s.minAddedOk = false;
				s.minAddedError = "ei ole korrektne kuupäev";
			}
		}
		
		try{
			Utils.isoDate(s.getMaxAdded());
			s.maxAddedOk = true;
		}
		catch(Exception e){
			if(s.maxAdded.length() == 0) s.maxAddedOk = true;
			else{
				s.maxAddedOk = false;
				s.maxAddedError = "ei ole korrektne kuupäev";
			}
		}
		
		try{
			Utils.isoDate(s.getMaxChanged());
			s.maxChangedOk = true;
		}
		catch(Exception e){
			if(s.maxChanged.length() == 0) s.maxChangedOk = true;
			else{
				s.maxChangedOk = false;
				s.maxChangedError = "ei ole korrektne kuupäev";
			}
		}
		
		try{
			Utils.isoDate(s.getMinChanged());
			s.minChangedOk = true;
		}
		catch(Exception e){
			if(s.minChanged.length() == 0) s.minChangedOk = true;
			else{
				s.minChangedOk = false;
				s.minChangedError = "ei ole korrektne kuupäev";
			}
		}
		
		s.ok = s.nameOk && s.descOk && s.minPriceOk && s.maxPriceOk && s.addedByOk && s.changedByOk && 
			s.minAddedOk && s.maxAddedOk && s.minChangedOk && s.maxChangedOk;
	}
}
