/* *
 * Use static array for NewsFeed
 * with constant MAX_SIZE
 * */

public class NewsFeed {

    private Post[] messages;
    private int size;
    public static final int MAX_SIZE = 25;

    public NewsFeed() {
    	messages = new Post[MAX_SIZE];
		size = 0;
    }

    public void add(Post message) {
      if (size < 25){
		messages[size] = message;
		size++;
	  }
    }

    public Post get(int index) {
	     return messages[index];
    }

    public int size() {
		return size;
    }

	  public void sort(){
			int i, j, argMin;
			Post tmp;
			for (i = 0; i < size - 1; i++) {
				argMin = i;
				for (j = i + 1; j < size(); j++) {
					if (messages[j].compareTo(messages[argMin]) < 0) {
						argMin = j;
					}
				}

  			tmp = messages[argMin];
  			messages[argMin] = messages[i];
  			messages[i] = tmp;
		  }

	  }

  	public NewsFeed getPhotoPost(){
  		NewsFeed photoFeed = new NewsFeed();
		for (int i = 0; i < size; i++){
			if (messages[i] instanceof PhotoPost) {
                photoFeed.add(messages[i]);
            }
        }	
		return photoFeed;
		}
  	

  	public NewsFeed plus(NewsFeed other){
		NewsFeed combinedFeed = new NewsFeed();
		int feedNum = 0, otherFeedNum = 0;
		int combinedSize = other.size() + this.size();
		//System.out.println(combinedSize);
		for (int i = 0;i < (combinedSize);i++){
			if (this.messages[feedNum] == null){
				for (; i < (combinedSize);i++){
					combinedFeed.add(other.messages[otherFeedNum]);
					otherFeedNum++;
				}
				break;
			}
			if (other.messages[otherFeedNum] == null){
				for (; i < (combinedSize);i++){
					combinedFeed.add(this.messages[feedNum]);
					feedNum++;
				}
				break;
			}
			if (this.messages[feedNum].compareTo(other.messages[otherFeedNum]) < 0){
				combinedFeed.add(this.messages[feedNum]);
				feedNum++;
			}
			else{
				combinedFeed.add(other.messages[otherFeedNum]);
				otherFeedNum++;
			}
		}
		return combinedFeed;
  	}


}
