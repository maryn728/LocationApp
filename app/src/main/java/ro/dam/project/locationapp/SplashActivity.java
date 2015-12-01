package ro.dam.project.locationapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ro.dam.project.locationapp.dao.CountryInfoDAO;
import ro.dam.project.locationapp.dao.UserDAO;
import ro.dam.project.locationapp.model.CountryInfo;
import ro.dam.project.locationapp.model.User;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start login activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);

                // Close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        UserDAO userDAO = new UserDAO(getApplicationContext());
        User user1 = userDAO.insertUser("Cristian","Olteanu","cristian.olteanu94@yahoo.com","12345","Romania");
        User user2 = userDAO.insertUser("Irina","Musulin","irina.musulin@yahoo.com","12345","Spain");
        User user3 = userDAO.insertUser("Maria","Nicula","maria.nicula@yahoo.com","12345","Germany");
        User user4= userDAO.insertUser("Miruna","Onuta","miruna.onuta@yahoo.com","12345","Italy");


        CountryInfoDAO countryInfoDAO = new CountryInfoDAO(getApplicationContext());
        CountryInfo countryInfo1= countryInfoDAO.insertCountryInfo("Romania","romania","Bucharest","Romanian",19942642,"Romania is a unitary semi-presidential republic located inSoutheastern Europe, bordering the Black Sea, between Bulgaria andUkraine. Romania derives from the Latin romanus, meaning \"citizen of Rome\". The first known use of the appellation was attested in the 16th-century by Italian humanists travelling in Transylvania, Moldavia, and Wallachia. Modern Romania emerged within the territories of the ancient Roman province of Dacia, and was formed in 1859 through a personal union of the Danubian Principalities of Moldavia and Wallachia. The new state, officially named Romania since 1866, gained independence from the Ottoman Empire in 1877. At the end of World War I, Transylvania, Bukovina and Bessarabia united with the sovereign Kingdom of Romania. During World War II, Romania was an ally of Nazi Germany against the Soviet Union, fighting side by side with the Wehrmacht until 1944, then it joined the Allied powers after being occupied by the Red Army forces. After the war, Romania became a socialist republic and member of the Warsaw Pact. After the 1989 Revolution, Romania began a transition back towards democracy and a capitalist market economy.");
        CountryInfo countryInfo2= countryInfoDAO.insertCountryInfo("Ireland","ireland","Dublin","Irish, English",6378000,"Ireland is an island in the North Atlantic separated from Great Britain to its east by the North Channel, the Irish Sea, and St George's Channel. It is the second-largest island of the British Isles, trailing only Great Britain, the third-largest in Europe, and the twentieth-largest on Earth.Politically, Ireland is divided between the Republic of Ireland (officially also named Ireland), which covers five-sixths of the island, and Northern Ireland, a part of the United Kingdom, which covers the remaining area and is located in the northeast of the island. In 2011 the population of Ireland was about 6.4 million, ranking it the second-most populous island in Europe after Great Britain. Just under 4.6 million live in the Republic of Ireland and just over 1.8 million live in Northern Ireland.");
        CountryInfo countryInfo3= countryInfoDAO.insertCountryInfo("United States","us","Washington D.C.","English",322014853 ,"The United States of America (USA), commonly referred to as the United States (U.S.) or America, is a federal republic composed of 50 states, a federal district, five major territories and various possessions. The 48 contiguous states and Washington, D.C., are in central North America between Canada and Mexico. The state of Alaska is in the north western part of North America and the state of Hawaii is an archipelago in the mid-Pacific. The territories are scattered about the Pacific Ocean and the Caribbean Sea. At 3.8 million square miles (9.842 million km2) and with over 320 million people, the country is the world's third or fourth-largest by total area and the third most populous. It is one of the world's most ethnically diverse and multicultural nations, the product of large-scale immigration from many countries. The geography and climate of the United States are also extremely diverse, and the country is home to a wide variety of wildlife.");
        CountryInfo countryInfo4= countryInfoDAO.insertCountryInfo("France","france","Paris","French",67107000 ,"France, officially the French Republic (French: République française), is a sovereign state comprising territory in western Europe and several overseas regions and territories. The European part of France, called Metropolitan France, extends from the Mediterranean Sea to the English Channel and the North Sea, and from the Rhine to the Atlantic Ocean. France spans 640,679 square kilometres (247,368 sq mi) and has a total population of 67 million. It is a unitary semi-presidential republic with the capital in Paris, the country's largest city and main cultural and commercial centre. The Constitution of France establishes the state as secular and democratic, with its sovereignty derived from the people.");
        CountryInfo countryInfo5= countryInfoDAO.insertCountryInfo("Germany","germany","Berlin","German",81083600 ,"Germany (German: Deutschland), officially the Federal Republic of Germany (German: Bundesrepublik Deutschland), is a federal parliamentary republic in western-central Europe. It includes 16 constituent states and covers an area of 357,021 square kilometres (137,847 sq mi) with a largely temperate seasonal climate. Its capital and largest city is Berlin. With 81 million inhabitants, Germany is the most populous member state in the European Union. After the United States, it is the second most popular migration destination in the world.");
        CountryInfo countryInfo6= countryInfoDAO.insertCountryInfo("Italy","italy","Rome","Italian",60795612 ,"Italy (Italian: Italia), officially the Italian Republic (Italian: Repubblica Italiana), is a unitary parliamentary republic in Europe. Italy covers an area of 301,338 km2 (116,347 sq mi) and has a largely temperate climate; due to its shape, it is often referred to in Italy as lo Stivale (the Boot).With 61 million inhabitants, it is the 4th most populous EU member state. Located in the heart of the Mediterranean Sea, Italy shares open land borders with France, Switzerland, Austria, Slovenia, San Marino and Vatican City.");
        CountryInfo countryInfo7= countryInfoDAO.insertCountryInfo("Norway","norway","Oslo","Norwegian, Lule Sami, Northern Sami, Southern Sami",5165802,"Norway, officially the Kingdom of Norway, is a sovereign and unitary monarchy whose territory comprises the western portion of the Scandinavian Peninsula plus Jan Mayen and the Arctic archipelago of Svalbard. The Antarctic Peter I Island and the sub-Antarctic Bouvet Island are dependent territories and thus not considered part of the Kingdom. Norway also lays claim to Queen Maud Land, a territory which is larger than Greenland, more than seven times the size of Norway proper, and about one-fifth of the Antarctic landmass. On most maps there had been an unclaimed area between Queen Maud Land and the South Pole until June 12, 2015 when Norway formally annexed that area.Until 1814, the Kingdom included the Faroe Islands (since 1035), Greenland (1261), and Iceland (1262), which was lost through the Treaty of Kiel. The Kingdom also included Shetland and Orkney until 1468, as well as the Hebrides and the Isle of Man from 1098 to 1266.");
    }
}
