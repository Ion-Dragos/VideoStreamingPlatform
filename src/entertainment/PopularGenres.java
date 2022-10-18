package entertainment;

public class PopularGenres {

    private String genre;
    private int count;

    public PopularGenres() {

    }

    public PopularGenres(final String genre, final int count) {
        this.genre = genre;
        this.count = count;
    }

    /**
     *
     * @return - returneaza genul unui film
     */
    public String getGenre() {
        return genre;
    }

    /**
     *
     * @param genre - seteaza genul unui film
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     *
     * @return - returneaza de cate ori a fost vazuta acea categorie de filme
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @param count - seteaza numarul de vizionari
     */
    public void setCount(final int count) {
        this.count = count;
    }

    /**
     *  de fiecare data cand vedem o anumita categorie, numaram
     */
    public void incrementCount() {
        count++;
    }
}
