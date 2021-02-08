package sgb.decoder;

import sgb.decoder.internal.Fields;

@Fields(fields = { "lat", "lon" }, serializedNames = { "latitude", "longitude" })
public final class EncodedGnssPosition implements HasFormatter {

	private final double lat;
	private final double lon;

	public EncodedGnssPosition(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double lat() {
		return lat;
	}

	public double lon() {
		return lon;
	}

	@Override
	public String toString() {
		return toStringDefault();
	}

}