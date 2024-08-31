package jp.co.example.ec202110d.form;

/**
 * 商品名の曖昧検索用のフォーム
 * 
 * @author NobutakaYoshida
 *
 */
public class HeaderForm {

		private String name;
		private Integer sortNum;

		public Integer getSortNum() {
			return sortNum;
		}

		public void setSortNum(Integer sortNum) {
			this.sortNum = sortNum;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "HeaderForm [name=" + name + "]";
		}
		
		
}
