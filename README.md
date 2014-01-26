# Markov Cluster Algorithm (MCL)
Author: Jiri Krizek

Supervisor: Jaroslav Kuchar

The Markov Clustering plugin for <a href="http://www.gephi.org">Gephi</a>. 
This plugin finds clusters in graph, which can be used in Social Network Analysis. 

Clustering on Graphs: The Markov Cluster Algorithm (MCL)
* MCL details are freely available at http://www.micans.org/mcl/


## Tutorial
You can start cluster finding using "Clustering" panel. This panel is usually on the left part of Gephi window. 
If you don't see this panel, enable it using "Window/Clustering" from the main menu.

From dropdown menu, select **Markov clustering**. 

Then you can edit algorithm parameters using button **Settings**:
![mclparams](https://raw.github.com/jaroslav-kuchar/MarkovClustering/master/images/mcl.png)

### Parameters
* **Add self loops to each node** - improves clusters computation (small simple path loops can complicate computation in odd powers, enabling this parameter resolves this)
* **Generate nodes with more clusters as standalone cluster** - In special cases (symmetric graph), node can fall into two different clusters at the same time. You can use this parameter to show this multiple nodes as new cluster (e. g. *Clusters: [1, 2] - 1 node*)
* **Power parameter** - affects time of computation and partially cluster granularity
* **Inflation parameter** - affects cluster granularity (bigger the number means more clusters)
* **Pruning parameter** - computed values below this number will be interpreted as zero values, this parameter speeds up the computation

After setting the parameters, you can start computation using **Run** button in
the Clustering panel.

## License
The GPL version 3, http://www.gnu.org/licenses/gpl-3.0.txt
