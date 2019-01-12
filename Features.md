# Features

## 1. User management - FERTIG

- [x] User entity und Service anpassen (@Chris)
- [x] PasswordEncoder einfügen und testUser anpassen (@Chris)
- [x] [addUserController](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/java/at/qe/sepm/skeleton/ui/controllers/AddUserController.java) anpassen (@Markus @Melanie)
- [x] [users.xhtml](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/webapp/admin/users.xhtml) anpassen und [formular](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/webapp/admin/create-user.xhtml) einfügen (@Andi)

### TODO/Notes

- NOTE: cNumber Getters und Setters müssen so geschrieben werden weil JSF diese sonst nicht erkennt
- [x] navbar anpassen (brauchen wir die tests?)
- [x] sollen in den views alle ROLES angezeigt werden oder nur die mit am meisten rechte?
- [ ] TODD: zb sicheres passwort generieren und per mail schicken
- [ ] BUG: user overview sortieren geht erst nachdem man in der suchleiste etwas gesucht hat(umstellung der List<> auf filteredUsers)

## 2. Equipment management & Opening Hours - FAST FERTIG

- [x] Equipment Entity und Service (@Johannes)
- [x] testEquipments hinzufügen (@Johannes)
- [x] EquipmentListController (@Melanie @Markus)
- [x] addEquipmentController (@Melanie @Markus)
- [x] view zur Übersicht aller Equipments und equipment hinzufügen **ADMIN** (@Andi)
- [x] OpeningHours Entity und Service (@Chris)
- [x] testÖffnungszeiten hinzufügen (@Chris)
- [ ] view zur Einsicht der Öffnungszeiten und zum ändern **ADMIN** (@Andi)
- [x] view zum Ansehen der equipmentDetails(Kommentare, Manuals) **?ALLe? = STUDENT** und Kommentare/Manuals hinzufügen/entfernen nur für **ADMIN**

### TODO/Notes

- [ ] Bug: beim ändern des eqipments wird die maximale ausleihdauer auf 0 gesetzt wenn man diese nicht verändert
- [x] TODO: erstellen von kommentaren
- [ ] Bug: beim erstellen der anleitungen/kommentare wird die tabelle wieder eingeklappt/es sind anfangs immer alle expansions ausgeklappt[so thread](https://stackoverflow.com/questions/43598420/how-to-keep-primefaces-rowexpansion-open-in-an-update-of-the-table)
- [x] TODO: up/download von manuals

## 3. Alle Reservations - IN ENTWICKLUNG

- [x] Reservation Entity (@Johannes)
- [x] testReservationen (@Johannes)
- [ ] ReservationListController (@Melanie @Markus)
- [ ] addReservationController (@Melanie @Markus)
- [ ] Bestätigungsmails (@Melanie @Markus)
- [ ] AddEquipmentGroupReservationController (@Melanie @Markus)
- [ ] EquipmentGroupListController (@Melanie @Markus)
- [ ] view zur Einsicht, erstellen & löschen der eigenen Reservierungen **EMPLOYEE** (@Andi @Chris)
- [ ] view zur Einsicht aller EquipmentGroupReservierungen(und löschen) **ADMIN** (@Andi @Chris)
- [ ] view zur Einsicht aller ausgeliehenen und überfälligen Reservierungen(und Rckgabebestätigung) **ADMIN** (@Andi @Chris)
- [ ] view zur Einsicht(und löschen) aller eigenen Reservierungen und reservierung hinzufügen **STUDENT** (@Andi @Chris)
- [ ] view zur Einsicht(und löschen) aller reservierungen **ADMIN** (@Andi @Chris)
- [ ] view zur Einsicht aller ausgeliehenen und überfälligen Equipments(und Rückgabebestätigungen) **ADMIN** (@Andi @Chris)

### TODO/Notes

- [x] TODO: Kaskadierung beim löschen in alle Richtungen (sollte passen)

## 4. EquipmentGroup management - FERTIG

- [x] equipmentgroup entity (@Johannes)
- [x] testEquipmentGroups (@Johannes)
- [x] EquipmentGroupListController (@Melanie @Markus)
- [x] AddEquipmentGroupController (@Melanie @Markus)
- [x] view zur Einsicht, erstellen & löschen der eigenen EquipmentGroups **EMPLOYEE** (@Andi)
- [x] view zur Einsicht und löschung aller EquipmentGroups **ADMIN** (@Andi)

### TODO/Notes

- [x] TODO: Kaskadierung beim löschen von equipmentgruppen eventuell besser umsetzen

## 6. Testing & Bugfixes

- [ ] ui sprache ist deutsch, alles was noch englisch ist anpassen
- [ ] bei allen zurück buttons bei denen die form validiert und bei leerem input gewarnt wird, beim zurück button `immediate="true"` attribut setzen.
- [ ] kontrolle ob alle features laut spezifikation existieren
- [ ] test entities für alles und dokumentation
- [ ] vagrantfile updaten
- [ ] unit tests
