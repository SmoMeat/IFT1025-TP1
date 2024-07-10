package dto;

import java.util.List;
import java.util.Map;

public class CoursDto {
    public String status;
    public String queryType;
    public boolean hasMoreResults;
    public String queryTypeLabel;
    public String allResultCount;
    public int currentPageResultCount;
    public int currentPage;
    public int resultsPerPage;
    public int loadedResultCount;
    public int totalPages;
    public String requestUrl;
    public String advancedSearchUrl;
    public String advancedSearchLabel;
    public int languageId;
    public Facets facets;
    public String progressBarPercent;

    public static class Facets {
        public Object cycle;
        public Object matiere;
        public Object modes_enseignement;
        public CoursTrimestre cours_trimestre;
        public Object particularites;
        public Object campus;
        public Object jourssemaines;
        public Object horaire;
        public Object faculte;

        public static class CoursTrimestre {
            public boolean used;
            public List<FacetOption> options;

            public static class FacetOption {
                public String className;
                public boolean disabled;
                public String label;
                public int value;
                public String documentCount;
                public boolean selected;
                public int order;
                public String advancedSearchUrl;
                public String advancedSearchLabel;
            }
        }
    }
}
