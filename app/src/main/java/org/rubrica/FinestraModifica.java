package org.rubrica;

public class FinestraModifica extends EditorPersona {
    
    DataManager dm;
    int id;
    FinestraPrincipale fp;

    public FinestraModifica(FinestraPrincipale fp, DataManager dm, int id) {
        setCampiDiTesto(dm.getPersona(id));
        this.dm = dm;
        this.id = id;
        this.fp = fp;
    }

    @Override
    protected void salva() {
        dm.modifica(id, new Persona(
            campiTesto[0].getText(),
            campiTesto[1].getText(),
            campiTesto[2].getText(),
            campiTesto[3].getText(),
            Integer.parseInt(campiTesto[4].getText())
        ));
        fp.generaTabella();
    }

    private void setCampiDiTesto(Persona p) {
        campiTesto[0].setText(p.getNome());
        campiTesto[1].setText(p.getCognome());
        campiTesto[2].setText(p.indirizzo);
        campiTesto[3].setText(p.getTelefono());
        campiTesto[4].setText(Integer.toString(p.eta));
    }
}
