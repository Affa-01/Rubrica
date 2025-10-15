package org.rubrica;

public class FinestraNuovo extends EditorPersona {
    DataManager dm;
    FinestraPrincipale fp;
    public FinestraNuovo(FinestraPrincipale fp, DataManager dm) {
        this.dm = dm;
        this.fp = fp;
    }

    @Override
    protected void salva() {
        dm.salva(new Persona(
            campiTesto[0].getText(),
            campiTesto[1].getText(),
            campiTesto[2].getText(),
            campiTesto[3].getText(),
            Integer.parseInt(campiTesto[4].getText())
        ));
        fp.generaTabella();
    }

}
