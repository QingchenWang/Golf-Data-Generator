import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;



public class Main {
	
	public static ArrayList<ArrayList<SummaryRecord>> _PGASummaryRecords;
	public static ArrayList<ArrayList<SummaryRecord>> _NationwideSummaryRecords;
	public static ArrayList<Double> _sampleScoringDistribution;
	public static ArrayList<Double>	_sampleScoringDistributionAverage;

	public static void main(String[] args){
		_PGASummaryRecords = new ArrayList<ArrayList<SummaryRecord>>();
		_NationwideSummaryRecords = new ArrayList<ArrayList<SummaryRecord>>();
		_sampleScoringDistribution = new ArrayList<Double>();
		_sampleScoringDistributionAverage = new ArrayList<Double>();
		
		run("PGA Tour Raw Data First - 2002.csv", true);
		run("PGA Tour Raw Data First - 2003.csv", true);
		run("PGA Tour Raw Data First - 2004.csv", true);
		run("PGA Tour Raw Data First - 2005.csv", true);
		run("PGA Tour Raw Data First - 2006.csv", true);
		run("PGA Tour Raw Data First - 2007.csv", true);
		run("PGA Tour Raw Data First - 2008.csv", true);
		run("PGA Tour Raw Data First - 2009.csv", true);
		run("PGA Tour Raw Data First - 2010.csv", true);
		
		run("Nationwide Tour Raw Data First - 2004.csv", false);
		run("Nationwide Tour Raw Data First - 2005.csv", false);
		run("Nationwide Tour Raw Data First - 2006.csv", false);
		run("Nationwide Tour Raw Data First - 2007.csv", false);
		run("Nationwide Tour Raw Data First - 2008.csv", false);
		run("Nationwide Tour Raw Data First - 2009.csv", false);
		run("Nationwide Tour Raw Data First - 2010.csv", false);
		
		getScoringDistribution("PGA Tour Raw Data First - 2002.csv-Cleaned.csv");
		getScoringDistribution("PGA Tour Raw Data First - 2003.csv-Cleaned.csv");
		getScoringDistribution("PGA Tour Raw Data First - 2004.csv-Cleaned.csv");
		getScoringDistribution("PGA Tour Raw Data First - 2005.csv-Cleaned.csv");
		getScoringDistribution("PGA Tour Raw Data First - 2006.csv-Cleaned.csv");
		getScoringDistribution("PGA Tour Raw Data First - 2007.csv-Cleaned.csv");
		getScoringDistribution("PGA Tour Raw Data First - 2008.csv-Cleaned.csv");
		getScoringDistribution("PGA Tour Raw Data First - 2009.csv-Cleaned.csv");
		getScoringDistribution("PGA Tour Raw Data First - 2010.csv-Cleaned.csv");
				
//		getScoringDistribution("Nationwide Tour Raw Data First - 2004.csv-Cleaned.csv");
//		getScoringDistribution("Nationwide Tour Raw Data First - 2005.csv-Cleaned.csv");
//		getScoringDistribution("Nationwide Tour Raw Data First - 2006.csv-Cleaned.csv");
//		getScoringDistribution("Nationwide Tour Raw Data First - 2007.csv-Cleaned.csv");
//		getScoringDistribution("Nationwide Tour Raw Data First - 2008.csv-Cleaned.csv");
//		getScoringDistribution("Nationwide Tour Raw Data First - 2009.csv-Cleaned.csv");
//		getScoringDistribution("Nationwide Tour Raw Data First - 2010.csv-Cleaned.csv");


		
		// Get delta-Nat-to-PGA scores
		StringBuffer scoreChangeNatToPGABuffer = new StringBuffer();
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < _NationwideSummaryRecords.get(i).size(); j++){
				for(int k = 0; k < _PGASummaryRecords.get(i+3).size(); k++){
					if(_NationwideSummaryRecords.get(i).get(j).playerName.equals(_PGASummaryRecords.get(i+3).get(k).playerName) 
							&& _NationwideSummaryRecords.get(i).get(j).numPlayed > 5 
							&& _PGASummaryRecords.get(i+3).get(k).numPlayed > 5){
						//scoreChangeNatToPGA.add(_PGASummaryRecords.get(i+3).get(k).averageRCombined - _NationwideSummaryRecords.get(i).get(j).averageRCombined);
						scoreChangeNatToPGABuffer.append(_NationwideSummaryRecords.get(i).get(j).playerName + "," + (i+2004) + "," + _NationwideSummaryRecords.get(i).get(j).averageRCombined + "," + _PGASummaryRecords.get(i+3).get(k).averageRCombined + "," + (_PGASummaryRecords.get(i+3).get(k).averageRCombined - _NationwideSummaryRecords.get(i).get(j).averageRCombined) + "\n");
					}
				}
			}
		}
		
		// Get delta-PGA-to-Nat scores
		StringBuffer scoreChangePGAToNatBuffer = new StringBuffer();
		for(int i = 1; i < 8; i++){
			for(int j = 0; j < _PGASummaryRecords.get(i).size(); j++){
				for(int k = 0; k < _NationwideSummaryRecords.get(i-1).size(); k++){
					if(_PGASummaryRecords.get(i).get(j).playerName.equals(_NationwideSummaryRecords.get(i-1).get(k).playerName)
							&& _PGASummaryRecords.get(i).get(j).numPlayed > 5
							&& _NationwideSummaryRecords.get(i-1).get(k).numPlayed > 5){
						//scoreChangePGAToNat.add(_NationwideSummaryRecords.get(i+3).get(k).averageRCombined - _PGASummaryRecords.get(i).get(j).averageRCombined);
						scoreChangePGAToNatBuffer.append(_PGASummaryRecords.get(i).get(j).playerName + "," + (i+2003) + "," + _PGASummaryRecords.get(i).get(j).averageRCombined + "," + _NationwideSummaryRecords.get(i-1).get(k).averageRCombined + "," + (_NationwideSummaryRecords.get(i-1).get(k).averageRCombined - _PGASummaryRecords.get(i).get(j).averageRCombined) + "\n");
					}
				}
			}
		}
		
		try{
			FileWriter outFile = new FileWriter("output data/scoreChangeNatToPGA.csv");
			PrintWriter out = new PrintWriter(outFile);
			out.print(scoreChangeNatToPGABuffer);
			out.close();
			
			FileWriter outFile2 = new FileWriter("output data/scoreChangePGAToNat.csv");
			PrintWriter out2 = new PrintWriter(outFile2);
			out2.print(scoreChangePGAToNatBuffer);
			out2.close();
			
			FileWriter outFile3 = new FileWriter("output data/totalSampleScoringDistribution.csv");
			PrintWriter out3 = new PrintWriter(outFile3);
			StringBuffer _70_half = new StringBuffer();
			StringBuffer _71_half = new StringBuffer();
			StringBuffer _72_half = new StringBuffer();
			StringBuffer _rest = new StringBuffer();
			for(int i = 0; i < _sampleScoringDistribution.size(); i++){
				if(_sampleScoringDistributionAverage.get(i) < 70.501)
					_70_half.append(_sampleScoringDistribution.get(i) + "," + _sampleScoringDistributionAverage.get(i) + "\n");
				else if(_sampleScoringDistributionAverage.get(i) < 71.501)
					_71_half.append(_sampleScoringDistribution.get(i) + "," + _sampleScoringDistributionAverage.get(i) +  "\n");
				else if(_sampleScoringDistributionAverage.get(i) < 72.501)
					_72_half.append(_sampleScoringDistribution.get(i) + "," + _sampleScoringDistributionAverage.get(i) +  "\n");
				else
					_rest.append(_sampleScoringDistribution.get(i) + "," + _sampleScoringDistributionAverage.get(i) +  "\n");
			}
			out3.print(_70_half + "\n" + "\n" + "\n" + "\n" + "\n");
			out3.print(_71_half + "\n" + "\n" + "\n" + "\n" + "\n");
			out3.print(_72_half + "\n" + "\n" + "\n" + "\n" + "\n");
			out3.print(_rest);
			out3.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Done!!");
	}
	
	public static void run(String fileName, boolean PGA){
		try{
			File file = new File("input data/" + fileName);
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			String tournament = null;
			ArrayList<Record> recordList = new ArrayList<Record>();
						
			double courseAdjustmentScoreR1 = 0, courseAdjustmentScoreR2 = 0, courseAdjustmentScoreR3 = 0, courseAdjustmentScoreR4 = 0;
			int parScore = 0;
			while ((text = reader.readLine()) != null){
				String[] string = text.split(",");
				String playerName = "";
				String rank = "";
				String r1 = "";
				String r2 = "";
				String r3 = "";
				String r4 = "";
				String purse = "";
				if(Character.getType(string[0].charAt(0)) == 24){
					tournament = string[0];
					
					double playerCountR1 = 0, playerCountR2 = 0, playerCountR3 = 0, playerCountR4 = 0;
					double totalScoreR1 = 0, totalScoreR2 = 0, totalScoreR3 = 0, totalScoreR4 = 0;
					double averageScoreR1 = 0, averageScoreR2 = 0, averageScoreR3 = 0, averageScoreR4 = 0;
					parScore = Integer.parseInt(string[1]);
					String courseAdjustLoopText = null;
					//TODO: this should work...
					reader.mark(10000);
//					courseAdjustLoopText = reader.readLine();
//					String[] parString = courseAdjustLoopText.split(",");
//					parScore = Integer.parseInt(parString[1]);
					while((courseAdjustLoopText = reader.readLine()) != null && 
							Character.getType(courseAdjustLoopText.charAt(0)) != 24){
						String[] courseAdjustLoopString = courseAdjustLoopText.split(",");
						
						if(!("WD".equals(courseAdjustLoopString[2]) || "WD".equals(courseAdjustLoopString[3]) || "WD".equals(courseAdjustLoopString[4]) || "WD".equals(courseAdjustLoopString[5])) &&
								!("MDF".equals(courseAdjustLoopString[2]) || "MDF".equals(courseAdjustLoopString[3]) || "MDF".equals(courseAdjustLoopString[4]) || "MDF".equals(courseAdjustLoopString[5])) &&
								!("DQ".equals(courseAdjustLoopString[2]) || "DQ".equals(courseAdjustLoopString[3]) || "DQ".equals(courseAdjustLoopString[4]) || "DQ".equals(courseAdjustLoopString[5])) &&
								!("-".equals(courseAdjustLoopString[2]) || "-".equals(courseAdjustLoopString[3]) || "-".equals(courseAdjustLoopString[4]) || "-".equals(courseAdjustLoopString[5]))){
							if(Character.isDigit(courseAdjustLoopString[2].charAt(0))){
								if(Integer.parseInt(courseAdjustLoopString[2]) > 50){
									playerCountR1++;
									totalScoreR1 += Integer.parseInt(courseAdjustLoopString[2]);
								}
							}
							if(Character.isDigit(courseAdjustLoopString[3].charAt(0))){
								if(Integer.parseInt(courseAdjustLoopString[3]) > 50){
									playerCountR2++;
									totalScoreR2 += Integer.parseInt(courseAdjustLoopString[3]);
								}
							}
							if(Character.isDigit(courseAdjustLoopString[4].charAt(0))){
								if(Integer.parseInt(courseAdjustLoopString[4]) > 50){
									playerCountR3++;
									totalScoreR3 += Integer.parseInt(courseAdjustLoopString[4]);
								}
							}
							if(Character.isDigit(courseAdjustLoopString[5].charAt(0))){
								if(Integer.parseInt(courseAdjustLoopString[5]) > 50){
									playerCountR4++;
									totalScoreR4 += Integer.parseInt(courseAdjustLoopString[5]);
								}
							}
						}
					}
					averageScoreR1 = totalScoreR1 / playerCountR1;
					averageScoreR2 = totalScoreR2 / playerCountR2;
					averageScoreR3 = totalScoreR3 / playerCountR3;
					averageScoreR4 = totalScoreR4 / playerCountR4;
					courseAdjustmentScoreR1 = parScore - averageScoreR1;
					courseAdjustmentScoreR2 = parScore - averageScoreR2;
					courseAdjustmentScoreR3 = parScore - averageScoreR3;
					courseAdjustmentScoreR4 = parScore - averageScoreR4;
					
					System.out.println(courseAdjustmentScoreR1 + "  " + courseAdjustmentScoreR2 + "  " + courseAdjustmentScoreR3 + "  " + courseAdjustmentScoreR4);

					reader.reset();
				}
//				else if("Par".equals(string[0])){
//					// just skip reading this line
//				}
				else{
					if(!("WD".equals(string[2]) || "WD".equals(string[3]) || "WD".equals(string[4]) || "WD".equals(string[5])) &&
							!("MDF".equals(string[2]) || "MDF".equals(string[3]) || "MDF".equals(string[4]) || "MDF".equals(string[5])) &&
							!("DQ".equals(string[2]) || "DQ".equals(string[3]) || "DQ".equals(string[4]) || "DQ".equals(string[5])) &&
							!("-".equals(string[2]) || "-".equals(string[3]) || "-".equals(string[4]) || "-".equals(string[5]))){
						
						if('T' == string[0].charAt(0)){
							rank = string[0].substring(1);
						}
						else{
							rank = string[0];
						}

						playerName = string[1];
						if(playerName.startsWith("x-"))
							playerName = playerName.substring(2);
						if(Integer.parseInt(string[2]) > 50)
							r1 = Double.toString(Integer.parseInt(string[2]) + courseAdjustmentScoreR1);
						else
							r1 = "IWD";
						if(Integer.parseInt(string[3]) > 50)
							r2 = Double.toString(Integer.parseInt(string[3]) + courseAdjustmentScoreR2);
						else
							r2 = "IWD";
						if(Character.isDigit(string[4].charAt(0))){
							if(Integer.parseInt(string[4]) > 50)
								r3 = Double.toString(Integer.parseInt(string[4]) + courseAdjustmentScoreR3);
							else
								r3 = "IWD";
						}
						else
							r3 = string[4];
						if(Character.isDigit(string[5].charAt(0))){
							if(Integer.parseInt(string[5]) > 50)
								r4 = Double.toString(Integer.parseInt(string[5]) + courseAdjustmentScoreR4);
							else
								r4 = "IWD";
						}
						else
							r4 = string[4];
						purse = string[9];
						Record record = new Record(playerName, tournament, rank, r1, r2, r3, r4, purse);
						recordList.add(record);
					}
				}
			}
			
			ArrayList<ArrayList<Record>> sortedRecords = new ArrayList<ArrayList<Record>>();
			ArrayList<String> existingNames = new ArrayList<String>();
			for(int i = 0; i < recordList.size(); i++){
				if(!existingNames.contains(recordList.get(i).playerName)){
					ArrayList<Record> playerRecord = new ArrayList<Record>();
					playerRecord.add(recordList.get(i));
					sortedRecords.add(playerRecord);
					existingNames.add(recordList.get(i).playerName);
				}
				else{
					for(int j = 0; j < sortedRecords.size(); j++){
						if(recordList.get(i).playerName.equals(sortedRecords.get(j).get(0).playerName)){
							sortedRecords.get(j).add(recordList.get(i));
						}
					}
				}
			}

			FileWriter outFile = new FileWriter("output data/" + fileName + ".csv");
			PrintWriter out = new PrintWriter(outFile);
			for(int i = 0; i < recordList.size(); i++){
				out.print(recordList.get(i).playerName + "," + recordList.get(i).tournament + "," + recordList.get(i).rank + "," + 
						recordList.get(i).r1 + "," + recordList.get(i).r2 + "," + recordList.get(i).r3 + "," + recordList.get(i).r4 + "," +
						recordList.get(i).purse + "\n");
			}
			out.close();
			
			FileWriter outFile2 = new FileWriter("output data/" + fileName + "-Cleaned.csv");
			PrintWriter out2 = new PrintWriter(outFile2);
			for(int i = 0; i < sortedRecords.size(); i++){
				for(int j = 0; j < sortedRecords.get(i).size(); j++){
					out2.print(sortedRecords.get(i).get(j).playerName + "," + sortedRecords.get(i).get(j).tournament + "," + sortedRecords.get(i).get(j).rank + "," + 
							sortedRecords.get(i).get(j).r1 + "," + sortedRecords.get(i).get(j).r2 + "," + sortedRecords.get(i).get(j).r3 + "," + sortedRecords.get(i).get(j).r4 + "," +
							sortedRecords.get(i).get(j).purse + "\n");
				}
				out2.print("\n");
			}
			out2.close();
			
			FileWriter outFile4 = new FileWriter("output data/" + fileName + "-Cleaned-Different.csv");
			PrintWriter out4 = new PrintWriter(outFile4);
			ArrayList<ArrayList<String>> outList = new ArrayList<ArrayList<String>>();
			for(int i = 0; i < sortedRecords.size(); i++){
				ArrayList<String> playerOutList = new ArrayList<String>();
				playerOutList.add(sortedRecords.get(i).get(0).playerName);
				for(int j = 0; j < sortedRecords.get(i).size(); j++){
					playerOutList.add(sortedRecords.get(i).get(j).r1);
				}
				for(int j = 0; j < sortedRecords.get(i).size(); j++){
					playerOutList.add(sortedRecords.get(i).get(j).r2);
				}
				for(int j = 0; j < sortedRecords.get(i).size(); j++){
					playerOutList.add(sortedRecords.get(i).get(j).r3);
				}
				for(int j = 0; j < sortedRecords.get(i).size(); j++){
					playerOutList.add(sortedRecords.get(i).get(j).r4);
				}
				outList.add(playerOutList);
			}
			for(int j = 0; j < 200; j++){
				for(int i = 0; i < outList.size(); i++){
					if(j < outList.get(i).size()){
						out4.print(outList.get(i).get(j) + ",");
					}
					else{
						out4.print(",");
					}
				}
				out4.print("\n");
			}
			out4.close();
			
			ArrayList<SummaryRecord> summaryRecords = new ArrayList<SummaryRecord>();
			ArrayList<String> summaryScoresName = new ArrayList<String>();
			ArrayList<Double> summaryScores = new ArrayList<Double>();
			ArrayList<Integer> totalScores = new ArrayList<Integer>();
			for(int i = 0; i < sortedRecords.size(); i++){
				double totalFinish = 0;
				double totalR1 = 0;
				double totalR2 = 0;
				double totalR3 = 0;
				double totalR4 = 0;
				double totalRCombined = 0;
				int totalPurse = 0;
				int finishCount = 0;
				int r1Count = 0;
				int r2Count = 0;
				int r3Count = 0;
				int r4Count = 0;
				int rCombinedCount = 0;
				int purseCount = 0;
				for(int j = 0; j < sortedRecords.get(i).size(); j++){
					if(Character.isDigit(sortedRecords.get(i).get(j).rank.charAt(0))){
						finishCount++;
						totalFinish += Integer.parseInt(sortedRecords.get(i).get(j).rank);
					}
					if(Character.isDigit(sortedRecords.get(i).get(j).r1.charAt(0))){
						r1Count++;
						totalR1 += Double.parseDouble(sortedRecords.get(i).get(j).r1);
						totalRCombined += Double.parseDouble(sortedRecords.get(i).get(j).r1);
					}
					if(Character.isDigit(sortedRecords.get(i).get(j).r2.charAt(0))){
						r2Count++;
						totalR2 += Double.parseDouble(sortedRecords.get(i).get(j).r2);
						totalRCombined += Double.parseDouble(sortedRecords.get(i).get(j).r2);
					}
					if(Character.isDigit(sortedRecords.get(i).get(j).r3.charAt(0))){
						r3Count++;
						totalR3 += Double.parseDouble(sortedRecords.get(i).get(j).r3);
						totalRCombined += Double.parseDouble(sortedRecords.get(i).get(j).r3);
					}
					if(Character.isDigit(sortedRecords.get(i).get(j).r4.charAt(0))){
						r4Count++;
						totalR4 += Double.parseDouble(sortedRecords.get(i).get(j).r4);
						totalRCombined += Double.parseDouble(sortedRecords.get(i).get(j).r4);
					}
					if(Character.isDigit(sortedRecords.get(i).get(j).purse.charAt(0))){
						purseCount++;
						totalPurse += Integer.parseInt(sortedRecords.get(i).get(j).purse);
					}
				}
				rCombinedCount = r1Count + r2Count + r3Count + r4Count;
				double averageFinish = totalFinish / finishCount;
				double averageR1 = totalR1 / r1Count;
				double averageR2 = totalR2 / r2Count;
				double averageR3 = totalR3 / r3Count;
				double averageR4 = totalR4 / r4Count;
				double averageRCombined = totalRCombined / rCombinedCount;
				double sumSquareR1 = 0;
				double sumSquareR2 = 0;
				double sumSquareR3 = 0;
				double sumSquareR4 = 0;
				for(int j = 0; j < sortedRecords.get(i).size(); j++){
					if(Character.isDigit(sortedRecords.get(i).get(j).r1.charAt(0)))
						sumSquareR1 += Math.pow(Double.parseDouble(sortedRecords.get(i).get(j).r1) - averageR1, 2);
					if(Character.isDigit(sortedRecords.get(i).get(j).r2.charAt(0)))
						sumSquareR2 += Math.pow(Double.parseDouble(sortedRecords.get(i).get(j).r2) - averageR2, 2);
					if(Character.isDigit(sortedRecords.get(i).get(j).r3.charAt(0)))
						sumSquareR3 += Math.pow(Double.parseDouble(sortedRecords.get(i).get(j).r3) - averageR3, 2);
					if(Character.isDigit(sortedRecords.get(i).get(j).r4.charAt(0)))
						sumSquareR4 += Math.pow(Double.parseDouble(sortedRecords.get(i).get(j).r4) - averageR4, 2);
				}
				double stdevR1 = Math.sqrt(sumSquareR1 / r1Count);
				double stdevR2 = Math.sqrt(sumSquareR2 / r2Count);
				double stdevR3 = Math.sqrt(sumSquareR3 / r3Count);
				double stdevR4 = Math.sqrt(sumSquareR4 / r4Count);
				

//				if(rCombinedCount >= 20){
					SummaryRecord sr = new SummaryRecord(sortedRecords.get(i).get(0).playerName, averageFinish, 
							averageR1, averageR2, averageR3, averageR4, averageRCombined, r1Count, r2Count, r3Count, r4Count,
							stdevR1, stdevR2, stdevR3, stdevR4, totalPurse, sortedRecords.get(i).size());
					summaryRecords.add(sr);
					
					summaryScoresName.add(sortedRecords.get(i).get(0).playerName);
					summaryScores.add(averageRCombined);
					totalScores.add(rCombinedCount);
//				}
			}
			if(PGA)
				_PGASummaryRecords.add(summaryRecords);
			else
				_NationwideSummaryRecords.add(summaryRecords);
			
			FileWriter outFile3 = new FileWriter("output data/" + fileName + "-Summary.csv");
			PrintWriter out3 = new PrintWriter(outFile3);
			for(int i = 0; i < summaryRecords.size(); i++){
//				if(summaryRecords.get(i).numPlayed >= 10){
					out3.print(summaryRecords.get(i).playerName + "," + summaryRecords.get(i).averageFinish + "," + 
							summaryRecords.get(i).averageR1 + "," + summaryRecords.get(i).stdevR1 + "," + summaryRecords.get(i).sampleSizeR1 + "," +
							summaryRecords.get(i).averageR2 + "," + summaryRecords.get(i).stdevR2 + "," + summaryRecords.get(i).sampleSizeR2 + "," +
							summaryRecords.get(i).averageR3 + "," + summaryRecords.get(i).stdevR3 + "," + summaryRecords.get(i).sampleSizeR3 + "," +
							summaryRecords.get(i).averageR4 + "," + summaryRecords.get(i).stdevR4 + "," + summaryRecords.get(i).sampleSizeR4 + "," +
							summaryRecords.get(i).totalPurse + "," + summaryRecords.get(i).numPlayed + "\n");
//				}
			}
			out3.close();
			
			FileWriter outFileScores = new FileWriter("output data/" + fileName + "-SummaryScores.csv");
			PrintWriter outScores = new PrintWriter(outFileScores);
			for(int i = 0; i < summaryScores.size(); i++){
				outScores.print(summaryScoresName.get(i) + "," + summaryScores.get(i) + "," + totalScores.get(i) + "\n");
			}
			outScores.close();

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void getScoringDistribution(String fileName){
		try{
			File file = new File("output data/" + fileName);
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			ArrayList<Double> scores = new ArrayList<Double>();
			String name = "";
			StringBuffer outputBuffer = new StringBuffer();
						
			while ((text = reader.readLine()) != null){
				if(!"".equals(text)){
					String[] split = text.split(",");
					name = split[0];
					if(Character.isDigit(split[3].charAt(0))){
						scores.add(Double.parseDouble(split[3]));
					}
					if(Character.isDigit(split[4].charAt(0))){
						scores.add(Double.parseDouble(split[4]));
					}
					if(Character.isDigit(split[5].charAt(0))){
						scores.add(Double.parseDouble(split[5]));
					}
					if(Character.isDigit(split[6].charAt(0))){
						scores.add(Double.parseDouble(split[6]));
					}
				}
				else{
					outputBuffer.append(name + ",");
					double sumScores = 0;
					for(int i = 0; i < scores.size(); i++){
						sumScores += scores.get(i);
					}
					double averageScore = sumScores / scores.size();
					for(int i = 0; i < scores.size(); i++){
						outputBuffer.append((scores.get(i) - averageScore) + ",");
						_sampleScoringDistribution.add(scores.get(i) - averageScore);
						_sampleScoringDistributionAverage.add(averageScore);
					}
					outputBuffer.append("\n");
					scores = new ArrayList<Double>();
				}
			}
			
			FileWriter outFileScores = new FileWriter("output data/" + fileName + "-ScoringDistribution.csv");
			PrintWriter outScores = new PrintWriter(outFileScores);
			outScores.print(outputBuffer);
			outScores.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static class Record{
		String playerName, tournament;
		String rank, r1, r2, r3, r4, purse;
		
		public Record(String pName, String event, String finish, String round1, String round2, String round3, String round4, String money){
			playerName = pName;
			tournament = event;
			rank = finish;
			r1 = round1;
			r2 = round2;
			r3 = round3;
			r4 = round4;
			purse = money;
		}
	}
	
	private static class SummaryRecord{
		String playerName;
		double averageFinish, averageR1, averageR2, averageR3, averageR4, averageRCombined;
		double sampleSizeR1, sampleSizeR2, sampleSizeR3, sampleSizeR4, stdevR1, stdevR2, stdevR3, stdevR4;
		int totalPurse, numPlayed;
		
		public SummaryRecord(String pName, double af, double ar1, double ar2, double ar3, double ar4, double arc,
				double ssr1, double ssr2, double ssr3, double ssr4,	double stdr1, double stdr2, 
				double stdr3, double stdr4, int tp, int np){
			playerName = pName;
			averageFinish = af;
			averageR1 = ar1;
			averageR2 = ar2;
			averageR3 = ar3;
			averageR4 = ar4;
			averageRCombined = arc;
			sampleSizeR1 = ssr1;
			sampleSizeR2 = ssr2;
			sampleSizeR3 = ssr3;
			sampleSizeR4 = ssr4;
			stdevR1 = stdr1;
			stdevR2 = stdr2;
			stdevR3 = stdr3;
			stdevR4 = stdr4;
			totalPurse = tp;
			numPlayed = np;
		}
	}
}