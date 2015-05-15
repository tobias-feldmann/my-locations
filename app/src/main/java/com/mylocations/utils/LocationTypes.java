package com.mylocations.utils;

import android.location.Location;

/**
 * Created by Tobias Feldmann on 13.05.15.
 */
public enum LocationTypes {

    anderes,
    Accounting,
    Flughafen,
    Freizeitpark,
    Aquarium,
    Kunstgalerie,
    Geldautomat,
    Bäckerei,
    Bank,
    Bar,
    Schönheitssalon,
    Fahrradladen,
    Buchladen,
    Kegelbahn,
    Busbahnhof,
    Cafe,
    Campingplatz,
    Autohändler,
    Autovermietung,
    Autowerkstatt,
    Autowaschstraße,
    Casino,
    Friedhof,
    Kirche,
    Rathaus,
    Kleidungsladen,
    Lebensmittelgeschäft,
    Gerichtsgebäude,
    Zahnarzt,
    Kaufhaus,
    Arzt,
    Elektriker,
    Elektronikladen,
    Botschaftsgebäude,
    Einrichtung,
    Finanzunternehmen,
    Feuerwehr,
    Blumenladen,
    Essen,
    Bestattungsunternehmen,
    Möbelgeschäft,
    Tankstelle,
    Generalunternehmer,
    Supermarkt,
    Fitnessstudio,
    Haarpflege,
    Hardwareladen,
    Gesundheit,
    Hindutempel,
    Lebensmittelladen,
    Krankenhaus,
    Versicherungsagentur,
    Juweliergeschäft,
    Wäscherei,
    Rechtsanwalt,
    Bücherei,
    Spirituosenladen,
    Gemeindeverwaltung,
    Schlosser,
    Unterkunft,
    Essenslieferer,
    Essensmitnahme,
    Moschee,
    Videothek,
    Kino,
    Spediteur,
    Museum,
    Nachtclub,
    Maler,
    Park,
    Parkplatz,
    Tierladen,
    Apotheke,
    Physiotherapeut,
    Kultstätte,
    Klempner,
    Polizei,
    Post,
    Immobilienmakler,
    Restaurant,
    Dachdecker,
    Camping,
    Schule,
    Schuhgeschäft,
    Einkaufszentrum,
    Wellnesscenter,
    Stadium,
    Lagerhalle,
    Geschäft,
    UBahnhof,
    Synagoge,
    Taxi,
    Bahnhof,
    Reisebüro,
    Universität,
    Tierarzt,
    Zoo;

    public static String getType(int position)
    {
        LocationTypes type = values()[position];
        return type.name();
    }
}
