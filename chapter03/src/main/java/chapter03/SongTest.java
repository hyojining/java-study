package chapter03;

public class SongTest {

	public static void main(String[] args) {
		Song song1 = new Song();
		song1.setTitle("좋은날");
		song1.setArtist("아이유");
		song1.setAlbum("Real");
		song1.setComposer("이민수");
		song1.setTrack(3);
		song1.setYear(2010);
		
		song1.show();
	}

}
