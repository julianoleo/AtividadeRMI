import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RoomManagerImpl extends UnicastRemoteObject implements RoomManager {

    private List<Room> roomList = new ArrayList<>();
    private List<String> guests = new ArrayList<>();

    RoomManagerImpl() throws RemoteException {
        super();
        roomList.add(new Room(10, 0, "quartos individuais", 55));
        roomList.add(new Room(20, 1, "quartos duplos", 75));
        roomList.add(new Room(5, 2, "quartos duplos", 80));
        roomList.add(new Room(3, 3, "quartos triplos", 150));
        roomList.add(new Room(2, 4, "quartos quádruplos", 230));
    }

    public String list() throws RemoteException {
        StringBuilder list = new StringBuilder();
        for (Room room : roomList) list.append(room.toString()).append("\n");
        return list.toString();
    }

    public String guests() throws RemoteException {
        StringBuilder list = new StringBuilder();
        for (String guest : guests) list.append(guest).append("\n");
        return list.toString();
    }

    public String reserve(int typeRoom, String nameGuests) throws RemoteException {
        return roomList.stream().parallel().filter(room -> room.getTipo().equals(typeRoom)).findAny().
                map(room -> {
                    if (room.getQuantDisp() == 0) {
                        return "Não temos mais quartos disponíveis do Tipo " + typeRoom;
                    } else {
                        room.setQuantDisp(room.getQuantDisp()-1);
                        guests.add(nameGuests);
                        return "Tipo: " + typeRoom + " Hospede: " + nameGuests;
                    }
                }).orElse("Falha ao criar reserva!!");
    }
}
