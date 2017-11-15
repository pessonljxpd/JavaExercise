package com.adark0915.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TestJsoup {
	private static final String TRENDING_JAVA_URL = "https://github.com/trending/java";

	public static void main(String[] args) throws IOException {

		getTrendingPeriod();

//		getRepoTrending();
		
//		getLanguages();
		
//		getOtherLanguages();

	}

	private static void getTrendingPeriod() {
		try {
			Document document = Jsoup.connect(TRENDING_JAVA_URL).get();
			Elements elements = document.getElementsByClass("select-menu-list").get(0).child(0).children();
			for (Element element:elements){
				String href = element.attr("href");
				String language = element.child(1).text();
				System.out.println(language + ":" + href);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void getOtherLanguages() {
		try {
			Document document = Jsoup.connect(TRENDING_JAVA_URL).get();
			Elements elements = document.getElementsByClass("select-menu-list").get(1).child(0).children();
			for (Element element:elements){
				String href = element.attr("href");
				String language = element.child(1).text();
				System.out.println(language + ":" + href);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void getLanguages() {
		try {
			Document document = Jsoup.connect(TRENDING_JAVA_URL).get();
			Elements elements = document.getElementsByClass("filter-list small language-filter-list").get(0).children();
			System.out.println("Elements:"+elements.size());
			StringBuilder sb = new StringBuilder("Languages[");
			for (Element element:elements){
				String language = element.child(0).text();
				sb.append(language).append(",");
				System.out.println(language + ":" + element.child(0).attr("href"));
			}
			sb.append("]");
			System.out.println(sb);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void getRepoTrending() {
		try {
			Document document = Jsoup.connect(TRENDING_JAVA_URL).get();
			Elements elements = document.getElementsByClass("col-12 d-block width-full py-4 border-bottom");
			for (Element element0 : elements) {
				String href = element0.getElementsByClass("d-inline-block col-9 mb-1").get(0)
						.getElementsByTag("a").attr("href");
				System.out.println(href);
				String repoDesc = element0.getElementsByClass("py-1").get(0).text();
				System.out.println(repoDesc);
				String style = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(0).attr("style");
				int indexOf = style.indexOf(":");
				String languageColor = style.substring(indexOf+1, indexOf+8);
				String language = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(1).text();
				System.out.println(language+":"+languageColor);
				
				String starHref = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(2).attr("href");
				System.out.println("starHref:"+starHref);
				String starsCount = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(2).text();
				System.out.println("starsCount:"+starsCount);

				String forkHref = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(3).attr("href");
				System.out.println("forkHref:"+forkHref);
				String forkCount = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(3).text();
				System.out.println("forkCount:"+forkCount);
				
				String buildByHref = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(4).attr("href");
				System.out.println("buildByHref:"+buildByHref);
				Elements imgElements = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(4).getElementsByTag("img");
				for (Element imgElement : imgElements) {
					String imgSrc = imgElement.attr("src");
					System.out.println("imgSrc:"+imgSrc);
				}
				
				String starsPeroid = element0.getElementsByClass("f6 text-gray mt-2").get(0).child(5).text();
				System.out.println("Today stars:"+starsPeroid);
				
				System.out.println("____________________________________________________________________________________");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
