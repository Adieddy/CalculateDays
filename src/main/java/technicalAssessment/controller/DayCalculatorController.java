package technicalAssessment.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import technicalAssessment.exception.UserException;
import technicalAssessment.service.DayCalculatorService;

@RestController
public class DayCalculatorController {
	
	@Autowired
	DayCalculatorService service;
	
	/**
	 * 
	 * This method would be used to check if web service is working fine or not.
	 * 
	 */
	@RequestMapping(value="/hello")
	public String checkConnection(){
		return "Connection's working fine";
	}
	
	/**
	 * 
	 * This method uses input from user in the URL of web service.
	 * Output would be a string in format DD MM YYYY, DD MM YYYY, difference
	 * 
	 */
	@RequestMapping(value="getDays", method=RequestMethod.GET)
	public String calculateDays(@RequestParam("input") String input){
		try{
			System.out.println("Input received: "+input);
			service.validateDates(input);
		}
		catch (UserException e) {
			return "Incorrect dates mentioned. Error reason: "+e.getMessage();
		}
		return service.calculateDays(input);
	}
	
	/**
	 * 
	 * This method can be called if input is taken from file.
	 * The file is already in project's directory.
	 * Input in that file should be in this format: DD MM YYYY, DD MM YYYY
	 * Filename: InputDates
	 * 
	 */
	@RequestMapping(value="getDaysUsingFile", method=RequestMethod.GET)
	public String calculateDaysUsingInputFile(){
		BufferedReader bufferedReader = null;
		try {
			String input=null;
			FileReader fileReader = new FileReader(new File("InputDates"));
			bufferedReader = new BufferedReader(fileReader);
			while((input=bufferedReader.readLine())!=null){
				service.validateDates(input);
				return service.calculateDays(input);
			}
		} catch (FileNotFoundException e) {
			return "Input file not present. Please check";
		} catch (IOException e) {
			return "Not able to read file. Please check";
		} catch (UserException e) {
			return "Incorrect dates mentioned. Error reason: "+e.getMessage();
		}
		finally {
			try {
				if(bufferedReader!=null){
					bufferedReader.close();
				}
			} catch (IOException e) {
				return "Unable to read file";
			}
		}
		return "Not able to complete this task";
	}
}
