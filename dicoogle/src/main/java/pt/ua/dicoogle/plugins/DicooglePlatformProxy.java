/**
 * Copyright (C) 2014  Universidade de Aveiro, DETI/IEETA, Bioinformatics Group - http://bioinformatics.ua.pt/
 *
 * This file is part of Dicoogle/dicoogle.
 *
 * Dicoogle/dicoogle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dicoogle/dicoogle is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dicoogle.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ua.dicoogle.plugins;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import pt.ua.dicoogle.core.ServerSettings;

import pt.ua.dicoogle.sdk.IndexerInterface;
import pt.ua.dicoogle.sdk.QueryInterface;
import pt.ua.dicoogle.sdk.StorageInputStream;
import pt.ua.dicoogle.sdk.StorageInterface;
import pt.ua.dicoogle.sdk.core.DicooglePlatformInterface;
import pt.ua.dicoogle.sdk.core.ServerSettingsReader;
import pt.ua.dicoogle.sdk.datastructs.Report;
import pt.ua.dicoogle.sdk.datastructs.SearchResult;
import pt.ua.dicoogle.sdk.task.JointQueryTask;
import pt.ua.dicoogle.sdk.task.Task;

/**
 * A proxy to the implementations of Plugin Controller.
 * 
 * @author psytek
 * @author Luís A. Bastião Silva <bastiao@ua.pt>
 */
public class DicooglePlatformProxy implements DicooglePlatformInterface {

    private final PluginController pluginController;
    
    public DicooglePlatformProxy(PluginController pluginController){
        this.pluginController = pluginController;
    }
    
    @Override
    public IndexerInterface requestIndexPlugin(String name) {
        Collection<IndexerInterface> indexers = pluginController.getIndexingPlugins(true);
        for(IndexerInterface index : indexers){
            if(index.getName().equals(name)) return index;
        }
        return null;
    }

    @Override
    public QueryInterface requestQueryPlugin(String name) {
        Collection<QueryInterface> queriers = pluginController.getQueryPlugins(true);
        for(QueryInterface querier : queriers){
            if(querier.getName().equals(name)) return querier;
        }
        
        return null;
    }

    @Override
    public Collection<IndexerInterface> getAllIndexPlugins() {
        return pluginController.getIndexingPlugins(false);
    }

    @Override
    public Collection<QueryInterface> getAllQueryPlugins() {
        return pluginController.getQueryPlugins(false);
    }

    @Override
    public StorageInterface getStoragePluginForSchema(String storage) {
        return pluginController.getStorageForSchema(storage);
    }

    @Override
    public StorageInterface getStorageForSchema(URI location) {
        return pluginController.getStorageForSchema(location);
    }

    @Override
    public Iterable<StorageInputStream> resolveURI(URI location) {
        return pluginController.resolveURI(location);
    }

    @Override
	public Collection<StorageInterface> getStoragePlugins(boolean onlyEnabled) {
		return pluginController.getStoragePlugins(onlyEnabled);
	}

    @Override
	public StorageInterface getStorageForSchema(String schema) {
		return pluginController.getStorageForSchema(schema);
	}

    @Override
	public Collection<QueryInterface> getQueryPlugins(boolean onlyEnabled) {
		return pluginController.getQueryPlugins(onlyEnabled);
	}

    @Override
	public List<String> getQueryProvidersName(boolean enabled) {
		return pluginController.getQueryProvidersName(enabled);
	}

    @Override
	public QueryInterface getQueryProviderByName(String name,
			boolean onlyEnabled) {
		return pluginController.getQueryProviderByName(name, onlyEnabled);
	}

    @Override
	public JointQueryTask queryAll(JointQueryTask holder, String query,
			Object... parameters) {
		return pluginController.queryAll(holder, query, parameters);
	}

    @Override
	public Task<Iterable<SearchResult>> query(String querySource, String query,
			Object... parameters) {
		return pluginController.query(querySource, query, parameters);
	}

    @Override
	public JointQueryTask query(JointQueryTask holder,
			List<String> querySources, String query, Object... parameters) {
		return pluginController.query(holder, querySources, query, parameters);
	}

    @Override
	public List<Task<Report>> index(URI path) {
		return pluginController.index(path);
	}

    @Override
	public List<Report> indexBlocking(URI path) {
		return pluginController.indexBlocking(path);
	}

    @Override
    public ServerSettingsReader getSettings() {
        return ServerSettings.getInstance();
    }
    
}
