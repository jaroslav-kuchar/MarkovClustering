package cz.cvut.fit.krizeji1.markov_cluster;

import org.gephi.utils.progress.ProgressTicket;

/**
 *
 * @author JiriKrizek
 */
class ProgressTicketMock implements ProgressTicket {

    public ProgressTicketMock() {
    }

    @Override
    public void finish() {
    }

    @Override
    public void finish(String string) {
    }

    @Override
    public void progress() {
    }

    @Override
    public void progress(int i) {
    }

    @Override
    public void progress(String string) {
    }

    @Override
    public void progress(String string, int i) {
    }

    @Override
    public void setDisplayName(String string) {
    }

    @Override
    public String getDisplayName() {
        return "test";
    }

    @Override
    public void start() {
    }

    @Override
    public void start(int i) {
    }

    @Override
    public void switchToDeterminate(int i) {
    }

    @Override
    public void switchToIndeterminate() {
    }
    
}
