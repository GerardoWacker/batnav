package batnav.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import batnav.utils.Logger;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigManager
{
   private final File dataDir;
   private final Gson gson;

   /**
    * Helps to manage configuration files more efficiently.
    *
    * @author Gerardo Wacker
    */
   public ConfigManager()
   {
      final AppDirs appDirs = AppDirsFactory.getInstance();
      this.gson = new GsonBuilder().create();
      this.dataDir = new File(appDirs.getUserDataDir(".batnav", "latest", "gw_jiv"));

      if (this.dataDir.mkdirs())
      {
         Logger.log("Creado directorio de archivos");
      }
   }

   /**
    * Saves a Gson JsonObject into a file in the program's directory.
    *
    * @param fileName   Filename to save.
    * @param jsonObject JsonObject to save. MUST NOT BE EMPTY!
    * @throws IOException exception
    */
   public void saveJson(final String fileName, final JsonObject jsonObject) throws IOException
   {
      Logger.log("Guardando archivo " + fileName + "...");

      // Create a Writer with the specified file name.
      Writer writer = new FileWriter(new File(this.getDataDir(), fileName));

      // Write the file.
      gson.toJson(jsonObject, writer);
      writer.flush();

      Logger.log("Guardado archivo " + fileName + ".");
   }

   /**
    * Reads a specified JSON file.
    *
    * @param fileName File name to read.
    * @return JSON object with the file's contents.
    * @throws Exception exception
    */
   public JSONObject loadJson(final String fileName) throws Exception
   {
      Logger.log("Leyendo archivo " + fileName + ".");
      final File file = new File(this.getDataDir(), fileName);

      if (file.exists())
      {
         String jsonText = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
         return new JSONObject(jsonText);
      }
      return null;
   }

   /**
    * @return File containing the program's directory.
    */
   public File getDataDir()
   {
      return dataDir;
   }
}
