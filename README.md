# [Semesteroppgave 2: “Fire på rad”](https://retting.ii.uib.no/inf101.v18.sem2/blob/master/SEM-2.md)


* **README**
* [Oppgavetekst](SEM-2.md)

Dette prosjektet inneholder [Semesteroppgave 2](SEM-2.md). Du kan også [lese oppgaven online](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.sem2/blob/master/SEM-2.md) (kan evt. ha små oppdateringer i oppgaveteksten som ikke er med i din private kopi).

**Innleveringsfrist:**
* Hele oppgaven skal være ferdig til **fredag 27. april kl. 2359** ([AoE](https://www.timeanddate.com/worldclock/fixedtime.html?msg=4&iso=20180427T2359&p1=3399))
* [Ekstra tips til innlevering](https://retting.ii.uib.no/inf101/inf101.v18/wikis/innlevering)

(Kryss av under her, i README.md, så kan vi følge med på om du anser deg som ferdig med ting eller ikke.)

**Utsettelse:** Hvis du trenger forlenget frist er det mulig å be om det (spør gruppeleder – evt. foreleser/assistenter hvis det er en spesiell situasjon). Hvis du ber om utsettelse bør du være i gang (ha gjort litt ting, og pushet) før fristen
   * En dag eller to går greit uten begrunnelse.
   * Eksamen er relativt tidlig i år, så vi vil helst unngå lange utsettelser.
   * Om det er spesielle grunner til at du vil trenge lengre tid, så er det bare å ta kontakt, så kan vi avtale noe. Ta også kontakt om du [trenger annen tilrettelegging](http://www.uib.no/student/49241/trenger-du-tilrettelegging-av-ditt-studiel%C3%B8p). 
   
# Fyll inn egne svar/beskrivelse/kommentarer til prosjektet under
* Levert av:  Joakim Algrøy (jal043)
* [ ] hele semesteroppgaven er ferdig og klar til retting!
* Code review:
   * [ ] jeg har fått tilbakemelding underveis fra @brukernavn, ...
   * [ ] jeg har gitt tilbakemelding underveis til @brukernavn, ...
* Sjekkliste:
   * [x] Kjørbart Fire på Rad-spill
   * [ ] Forklart designvalg, hvordan koden er organisert, abstraksjon, og andre ting 
   * [ ] Tester
   * [ ] Dokumentasjon (JavaDoc, kommentarer, diagrammer, README, etc.)
   * [x] Fornuftige navn på klasser, interfaces, metoder og variabler
   * [x] Fornuftige abstraksjoner og innkapsling (bruk av klasser, interface, metoder, etc.)

## Oversikt

Jeg har implementert et grafisk fire-på-rad spill med et space-tema. Man kan enten spille lokal 2-player, eller spille mot en AI jeg har implementert. AIen heter HAL 9000 og er inspirert av filmen *2001: A space odyssey*.

Programmet består av ~1850 linjer med kode fordelt på 22 klasser (inkludert tester og generatorer), 5 interfaces og 2 enums. 

Jeg har forsøkt å skrive et oversiktlig program med abstraksjon der det er hensiktsmessig, og forsøkt å generalisere ting for lett utvidelse.

### Bruk
* For å starte programmet kjør: `inf101.v18.sem2.Main`
* Velg 2-player eller å spille mot AI, velg så navn og brikker. Man kan legge brikker ved å klikke på en kolonne eller bruke 1-7 på tastaturet.

## Designvalg
*(hvordan du har valgt å løse oppgaven)*
### GUI

Grafikken er implementert med JavaFX. Jeg har tre klasser ([TitleScene](src/inf101/v18/sem2/gui/TitleScene.java), [GetPlayerScene](src/inf101/v18/sem2/gui/GetPlayerScene.java) og [GameScene](src/inf101/v18/sem2/gui/GameScene.java)) som utvider `javafx.scene.Scene` og setter opp ulike visninger. Disse tre klassene gjør mesteparten av jobben for grafikken, og jeg mener det fungerer bra.
`GameScene` har en `AnimationTimer` som oppdaterer spillet 60 ganger i sekundet. Dette la jeg til for å kunne animere at brikke faller. `AnimationTimer` forenklet også litt ved at event-handlers ikke trenger å tegne brettet på nytt. Jeg fikk idèen til å bruke `AnimationTimer` fra lab 1, og så også på hvordan de fallende ballene var implementert i lab 1.

Jeg har en hjelpe-klasse [GuiUtil](src/inf101/v18/gui/GuiUtil.java) som har statiske konstanter og metoder relatert til grafikk, som jeg ikke følte hørte hjemme i andre klasser.
Min laptop har høyoppløselig skjerm (13" med 3200x1800), så jeg la til en skaleringsfaktor `GuiUtil.SF` som ganges med alle dimensjoner for grafiske objekter. For 1080p kan denne settes til 1. Det finnes nok bedre måter å gjøre dette på, men jeg har ikke brukt så mye grafikk før og dette var en enkel løsning.

### Game

Logikken og hendelsene i selve spillet er implementert i klassen [Game](src/inf101/v18/sem2/game/Game.java). Den holder styr på spillere, brettet og spillets nåværende tilstand, og håndterer bruker-input.
Metoden `drop()` trigges enten av en bruker eller en AI, og slipper en brikke i den oppgitte kolonnen.
Når en brikke slippes opprettes en [FallingDisc](src/inf101/v18/sem2/game/objects/FallingDisc.java) som Game lagrer. 
Når `Game.step()` kalles oppdateres `FallingDisc`, og hvis den har landet kalles `nextTurn()` som gir turen til neste spiller, og utløser et trekk hvis det er en AI sin tur.
Game har en `int turn` som teller opp for hvert trekk, slik at man kan hente hvem sin tur det er med `turn % 2`.

`game.draw()` tegner selve spill-brettet v.h.a. en GraphicsContext.

Ellers består `Game` mye av set- og get-metoder.

### Spillere

Spillere er av typen [IPlayer](src/inf101/v18/sem2/player/IPlayer.java), som er en interface som implementeres av to klasser, [Player](src/inf101/v18/sem2/player/Player.java) og [HAL](src/inf101/v18/sem2/player/HAL.java) (`HAL` implementerer `IAI extends IPlayer`). 
Spillere har et navn og en brikke. 

AI-spillere har i tillegg en metode `getMove(Game game)` som returnerer et trekk for det gitte spillet.

**HAL**

AIet mitt, [HAL](src/inf101/v18/sem2/player/HAL.java), er basert på ett sjakk-AI jeg har jobbet med i løpet av semesteret. Det bruker rekursjon for å simulere flere trekk fremover, og gir en poengsum til trekket basert på posisjonen på brettet etter de simulerte trekkene gjennomføres.
Det viste seg vanskeligere enn jeg hadde tenkt å gi en god tallverdi på det nåværende brettet (`ratePosition()`), så for øyeblikket gir den bare poeng om man har fire på rad, og minus-poeng om motstanderen har det.
(I sjakk var dette enklere, man kan f.eks. bare se på hvilke brikker som er igjen på brettet). Til gjengjeld kan man her søke litt dypt (5 trekk frem går veldig raskt).

Det er relativt enkelt å slå maskinen hvis man følger med. Den gjør en del tabber, men hvis man har tre på rad og den har mulighet til å blokkere, så gjør den det.

### Brett

Jeg har laget en enkel generisk 2d-grid i [IGrid](src/inf101/v18/sem2/datastructures/IGrid.java) og [Grid](src/inf101/v18/sem2/datastructures/Grid.java).
[IBoard](src/inf101/v18/sem2/datastructures/IBoard.java) og [Board](src/inf101/v18/sem2/datastructures/Board.java) er generiske brett hvor man kan legge til elementer i kolonner, og elementene legger seg i nederste ledige posisjon. 
Man kan også fjerne øverste element i en kolonne. `Board` bruker en `Grid` til å lagre elementene. Jeg vurderte å implementere `Board` som en samling av stacks, siden hver kolonne oppfører seg som en stack, men jeg ønsket også å aksessere elementer med x-y-koordinater så en grid fungerte greit.

`Game` bruker `Board<Disc>` til å representere brettet. Tomme posisjoner har element null. 

[Disc](src/inf101/v18/sem2/game/objects/Disc.java) er en enum som er brikkene i spillet. En `Disc` tegnes med `Disc.draw()`, som henter et tilhørende bilde fra `GuiUtil.getDiscImage()`.

### Bruk av abstraksjon
*(hvordan du har valgt objekter/klasser for å representere ting i spillet)*

### Erfaring – hvilke valg viste seg å være gode / dårlige?
*(designerfaringer – er det noe du ville gjort annerledes?)*

## Testing
*(hvordan du har testet ting)*

## Funksjonalitet, bugs
*(hva virker / virker ikke)*

## Evt. erfaring fra code review
*(lærte du noe av å gå gjennom din eller andres kode?)*

## Annet
*(er det noe du ville gjort annerledes?)*
