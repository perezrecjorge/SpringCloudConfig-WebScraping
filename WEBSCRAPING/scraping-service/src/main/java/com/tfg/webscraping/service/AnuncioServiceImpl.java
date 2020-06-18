package com.tfg.webscraping.service;

import com.tfg.webscraping.model.AnuncioDTOentrada;
import com.tfg.webscraping.model.AnuncioDTOsalida;
import com.tfg.webscraping.model.domain.Anuncio;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnuncioServiceImpl implements AnuncioService {

    /**
     * MÉTODO PRINCIPAL QUE CONCATENA LAS DOS BUSQUEDAS DE LOS DIFERENTES METODOS Y LO DEVUELVE
     * @param datosentrada
     * @return
     */
    @Override
    public List<AnuncioDTOsalida> getAllAnuncios(AnuncioDTOentrada datosentrada) {
        return java.util.stream.Stream.concat(scrapeoMilAnunciosJSoup(datosentrada).stream(), scrapeoWallapopSelenium(datosentrada).stream()).collect(Collectors.toList());
    }

    public List<AnuncioDTOsalida> scrapeoMilAnunciosJSoup(AnuncioDTOentrada datosentrada) {

        List<AnuncioDTOsalida> anunciosMilAnuncios = new ArrayList<AnuncioDTOsalida>();

        System.out.println(datosentrada.getMarcaCar() +" + "+ datosentrada.getModeloCar());
        String url = "https://www.milanuncios.com/" + datosentrada.getMarcaCar() + "-" + datosentrada.getModeloCar() + "-de-segunda-mano/?fromSearch=1/";

        // Compruebo si me da un 200 al hacer la petición
        if (getStatusConnectionCode(url) == 200) {

            // Obtengo el HTML de la web en un objeto Document
            Document document = getHtmlDocument(url);

            // Busco todas las entradas que estan dentro de:
            Elements entradas = document.select("div.aditem-detail-image-container");
            System.out.println("Número de entradas en la página inicial de Milanuncios: " + entradas.size() + "\n");

            // Paseo cada una de las entradas
            for (Element elem : entradas) {
                String titulo = elem.getElementsByClass("aditem-detail-title").text();
                String enlace = elem.select("div > a").first().absUrl("href");
                //String region = elem.getElementsByClass("list-location-region").text().toUpperCase();
                String precio = elem.getElementsByClass("aditem-price").text();
                String ano = elem.getElementsByClass("ano tag-mobile").text();
                String kms = elem.getElementsByClass("kms tag-mobile").text();
                //String combustible = elem.getElementsByClass("die tag-mobile").text();
                //String puertas = elem.getElementsByClass("ejes tag-bile").text();
                String cvs = elem.getElementsByClass("cc tag-mobile").text();
                //String cambio = elem.getElementsByClass("cmanual tag-mobile").text();

                anunciosMilAnuncios.add(new AnuncioDTOsalida(titulo, precio, ano, cvs, kms, enlace));

            }

        } else
            System.out.println("El Status Code no es OK es: " + getStatusConnectionCode(url));

        return anunciosMilAnuncios;
    }

    /**
     * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
     * EJM:
     * 200 OK			300 Multiple Choices
     * 301 Moved Permanently	305 Use Proxy
     * 400 Bad Request		403 Forbidden
     * 404 Not Found		500 Internal Server Error
     * 502 Bad Gateway		503 Service Unavailable
     *
     * @param url
     * @return Status Code
     */
    public static int getStatusConnectionCode(String url) {

        Connection.Response response = null;

        try {
            response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
        }
        return response.statusCode();
    }

    /**
     * Con este método devuelvo un objeto de la clase Document con el contenido del
     * HTML de la web que me permitirá parsearlo con los métodos de la librelia JSoup
     *
     * @param url
     * @return Documento con el HTML
     */
    public static Document getHtmlDocument(String url) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (IOException ex) {
            System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
        }
        return doc;
    }


    public List<AnuncioDTOsalida> scrapeoWallapopSelenium(AnuncioDTOentrada datosentrada) {

        String agreeButtonXpath = "//*[@id=\"didomi-notice-agree-button\"]";

        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.navigate().to("https://es.wallapop.com/search?category_ids=100&latitude=40.4893538&longitude=-3.6827461&brand="+datosentrada.getMarcaCar()+"&model="+datosentrada.getModeloCar());

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(agreeButtonXpath)));
        driver.findElement(By.xpath(agreeButtonXpath)).click();

        String listaAnuncioCompletaXpath = "//*[@id=\"main-search-container\"]";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(listaAnuncioCompletaXpath)));

        String anunciosXpath = "//*[@id=\"main-search-container\"]/div";
        List<WebElement> anunciosList = driver.findElements(By.xpath(anunciosXpath));

        List<AnuncioDTOsalida> anunciosWallapop = new ArrayList<AnuncioDTOsalida>();

        int cont = 1;
        // Print the languages
        for (WebElement anuncio : anunciosList) {
            try {
                String textotitulo = anuncio.getText();

                //String[] partes = textotitulo.split("\\n");

                String[] partes = textotitulo.split("\\R");

                if (partes.length == 4) {

                    String att = partes[2].replace(" ", ",");

                    try {
                        String[] atts = att.split(",");
                        //String combustible = atts[0];
                        //String cambio = atts[1];
                        String cvs = atts[2] + "cv";
                        String ano = atts[4];
                        String kms = atts[5] + "km";

                        String xpathenlace = "//*[@id=\"main-search-container\"]/div[" + cont + "]/div/div[2]/a";
                        String enlace = anuncio.findElement(By.xpath(xpathenlace)).getAttribute("href");

                        anunciosWallapop.add(new AnuncioDTOsalida(partes[1], partes[0], ano, cvs, kms, enlace));

                    } catch (ArrayIndexOutOfBoundsException a) {
                        System.out.println("ERROR EN EL SCRAPEO");
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Se han imprimido todos los anuncios");
            }
            cont++;
        }

        driver.close();

        return anunciosWallapop;
    }



}
