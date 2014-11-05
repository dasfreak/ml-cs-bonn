package ml_ex1;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	public String name; //name of the node read from file
	public int index; //column of the node in file
	public double entropy;
	public double all_records;
	public double information_gain;
	public Attribute attribute; //attribute of the node
	
	public ArrayList<Node> children;
    public Node parent=null;
    public List<Segment> segments; 
    
	
	//do not know yet if class_name is necessary here, we will see later on
	public Node(int index, Attribute attribute, String node_name){
		segments=new ArrayList<Segment>();
		this.name=node_name;
		this.attribute=attribute;
		this.index=index; //column in our data set
	}
	
	
	/* extracts segments for certain data column and saves this segment into segments list */
	public void extractSegments(){
		for (int i=0; i<DataStructureSingleton.getInstance1().length ; i++){
			Segment temp=new Segment(DataStructureSingleton.getInstance1()[i][index]);
			
			if (segments.isEmpty()){
				segments.add(temp);
				if ((boolean) DataStructureSingleton.getInstance1()[0][DataStructureSingleton.getInstance1()[0].length-1].getData())
					segments.get(0).yes_number=1;
				else
					segments.get(0).no_number=1;
			}
			else{
				int found=0;
				for(int j=0; j<segments.size(); j++){
					if (segments.get(j).attr.getData().equals(temp.attr.getData())){
						found++;
						segments.get(j).numer_of_occurrences++;
						if ((boolean) DataStructureSingleton.getInstance1()[i][DataStructureSingleton.getInstance1()[0].length-1].getData())
							segments.get(j).yes_number++;
						else
							segments.get(j).no_number++;
					}
				}
				if (found==0){
					segments.add(temp);
					if ((boolean) DataStructureSingleton.getInstance1()[i][DataStructureSingleton.getInstance1()[0].length-1].getData())
						segments.get(segments.size()-1).yes_number++;
					else
						segments.get(segments.size()-1).no_number++;
				}
				else{
					found=0;
				}
			}
		}
	}
	
	public void calculateInformationGain(double entropy_parent, double range){	
		
		
		if (this.attribute instanceof Categorical || this.attribute instanceof Binary){
			extractSegments();
			
			/**uncomment if you want to see how many yes and no are there for each segment **/
//			System.out.println("column: "+index+" segments size: "+segments.size());
//			for (int s=0; s<segments.size(); s++){
//				System.out.println("segment: "+s+ ", number of yes: "+ segments.get(s).yes_number);
//				System.out.println("segment: "+s+ ", number of no: "+ segments.get(s).no_number);
//			}
//			System.out.print('\n');
			
			double entropy_temp=0;
			for (int i=0; i<segments.size(); i++){
				double all_for_current_segment=segments.get(i).yes_number+ segments.get(i).no_number;
				entropy_temp+= (all_for_current_segment/range)*Entropy.calcEntropy(segments.get(i).yes_number, segments.get(i).no_number);
//				System.out.println(all_for_current_segment);
			}
			
			this.information_gain=entropy_parent-entropy_temp;
			
		}
		/**TODO: sort and split on numerical data**/
//		else if (this.attribute instanceof Numerical){
//			System.out.println("2");
//		}

	}
}
