

package org.cf.cloud.servicebroker.memsql.database;

import org.cf.cloud.servicebroker.memsql.service.MemSQLClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public abstract class Db {
   /* private static final Set<String> _obedientCommands = new HashSet();
    final MemSQLClient _memsqlClient;
    final String _name;


    public DB(MemSQLClient memsqlClient, String name) {
        if(!this.isValidName(name)) {
            throw new IllegalArgumentException("Invalid database name format. Database name is either empty or it contains spaces.");
        } else {
            this._memsqlClient = memsqlClient;
            this._name = name;

        }
    }

    private boolean isValidName(String dbname) {
        return dbname.length() != 0 && !dbname.contains(" ");
    }

    public String getName() {
        return this._name;
    }


    public String toString() {
        return this._name;
    }
*/

}
